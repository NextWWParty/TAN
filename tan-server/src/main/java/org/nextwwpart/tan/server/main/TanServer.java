package org.nextwwpart.tan.server.main;

import org.nextwwpart.tan.common.classLoader.WebClassLoader;
import org.nextwwpart.tan.common.dealThreads.SocketDealThread;
import org.nextwwpart.tan.common.utils.SerializeUtil;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 〈一句话描述功能〉<br>
 * 接入数据——>进入处理类——>处理返回结果
 *
 * @author 18070948
 * @date: 2019/1/14 21:04
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TanServer {
    private ServerSocket serverSocket;

    public TanServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);
    }

    /**
     * startReceiving
     *
     * @throws IOException
     */
    public void startReceiving() throws IOException {
        System.out.println("服务器启动-------------");
        int connectCount = 0;
        while (true) {
            //此处应该有并发数控制CountDownLatch
            Socket socket = serverSocket.accept();
            try {
                connectCount++;
                System.out.println("第" + connectCount + "次接入："+socket.getRemoteSocketAddress());
                byte[] bufferSizebytes = new byte[4];
                socket.getInputStream().read(bufferSizebytes);
                int bufferSize = SerializeUtil.BytesToInt(bufferSizebytes, 0);
                if (bufferSize == 0) {
                    break;
                } else {
                    byte[] strBuffer = new byte[bufferSize];
                    socket.getInputStream().read(strBuffer);
                    String dealThreadName = new String(strBuffer);
                    //最好保存一下线程实例，不必每次都通过反射构建对象
                    socket.getInputStream().read(bufferSizebytes);
                    bufferSize = SerializeUtil.BytesToInt(bufferSizebytes, 0);
                    byte[] classDataBuffer = new byte[bufferSize];
                    socket.getInputStream().read(classDataBuffer);
                    WebClassLoader webClassLoader = new WebClassLoader(classDataBuffer);
                    Class dealThreadClass = webClassLoader.loadClass(dealThreadName);
                    Constructor constructor = dealThreadClass.getConstructor(Socket.class);
                    SocketDealThread socketDealThread = (SocketDealThread) constructor.newInstance(socket);
                    socketDealThread.start();
                }
            } catch (Throwable e) {
              System.out.println(e);
            }
        }
    }

}
