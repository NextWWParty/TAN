package org.nextwwpart.tan.server.main;

import org.nextwwpart.tan.common.dealThreads.SocketDealThread;
import org.nextwwpart.tan.common.utils.ClassSenderUtil;

import java.io.IOException;
import java.lang.reflect.Constructor;
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
                System.out.println("第" + connectCount + "次接入：" + socket.getRemoteSocketAddress());
                Class dealThreadClass = ClassSenderUtil.receiveClass(socket);
                Constructor constructor = dealThreadClass.getConstructor(Socket.class);
                SocketDealThread socketDealThread = (SocketDealThread) constructor.newInstance(socket);
                socketDealThread.start();
            } catch (Throwable e) {
                System.out.println(e);
            }
        }
    }

}
