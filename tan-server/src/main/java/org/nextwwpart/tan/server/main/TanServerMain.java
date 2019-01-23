package org.nextwwpart.tan.server.main;

import java.io.IOException;

/**
 * 〈一句话描述功能〉<br>
 *
 * @author 18070948
 * @date: 2019/1/22 10:39
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TanServerMain {
    public static void main(String args[]) throws IOException {
        TanServer tanServer = new TanServer(10000);
        tanServer.startReceiving();
    }
}
