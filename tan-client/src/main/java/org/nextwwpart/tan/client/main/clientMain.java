package org.nextwwpart.tan.client.main;

import org.nextwwpart.tan.client.socketlThreads.SocketDealThread_1;
import org.nextwwpart.tan.common.utils.ClassSenderUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 〈一句话描述功能〉<br>
 *
 * @author 18070948
 * @date: 2018/12/30 16:35
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class clientMain {
    public static void main(String[] args) throws Exception {
        // 请求指定ip和端口号的服务器
        Socket socket = new Socket("127.0.0.1", 10001);
        Class sendClass = SocketDealThread_1.class;
        ClassSenderUtil.sendClass(socket, sendClass);
        while (true) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // 接收控制台的输入
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String info = input.readLine();
            out.println(info);
            String str = in.readLine();
            System.out.println("客户端显示--》服务器的信息：" + str);
        }
    }
}
