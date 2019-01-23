package org.nextwwpart.tan.client.main;

import org.nextwwpart.tan.client.socketlThreads.SocketDealThread_1;
import org.nextwwpart.tan.common.classLoader.ClassToBytes;
import org.nextwwpart.tan.common.utils.SerializeUtil;

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
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedReader input = null;
        // 请求指定ip和端口号的服务器
        socket = new Socket("127.0.0.1", 10000);
        String dealThreadName = SocketDealThread_1.class.getName();
        socket.getOutputStream().write(SerializeUtil.IntToBytes(dealThreadName.length()));
        byte[] buffer = dealThreadName.getBytes();
        socket.getOutputStream().write(buffer);
        String root = SocketDealThread_1.class.getResource("").getPath();
        byte[] classData = ClassToBytes.loadClassData(SocketDealThread_1.class.getSimpleName(), root);
        if (classData!=null) {
            socket.getOutputStream().write(SerializeUtil.IntToBytes(classData.length));
            socket.getOutputStream().write(classData);
            while (true) {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                // 接收控制台的输入
                input = new BufferedReader(new InputStreamReader(System.in));
                String info = input.readLine();
                out.println(info);
                String str = in.readLine();
                System.out.println("客户端显示--》服务器的信息：" + str);
            }
        }
        socket.close();
    }
}
