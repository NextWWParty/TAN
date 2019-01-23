package org.nextwwpart.tan.client.socketlThreads;

import org.nextwwpart.tan.common.dealThreads.SocketDealThread;

import java.io.*;
import java.net.Socket;

/**
 * 〈一句话描述功能〉<br>
 *
 * @author 18070948
 * @date: 2019/1/22 15:11
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SocketDealThread_1 extends SocketDealThread {
    public SocketDealThread_1(Socket socket) {
        super(socket);
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        while (true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = in.readLine();
                if (null == str) {
                    break;
                }
                System.out.println("服务器显示-->客户端输入数据：" + str);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String info = "server return";
                out.println(info);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
