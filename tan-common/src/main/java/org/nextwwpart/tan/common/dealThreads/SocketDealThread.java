package org.nextwwpart.tan.common.dealThreads;

import java.net.Socket;

/**
 * 〈一句话描述功能〉<br>
 *
 * @author 18070948
 * @date: 2019/1/22 15:01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class SocketDealThread extends Thread {
    protected Socket socket;

    public SocketDealThread(Socket socket) {
        this.socket = socket;
    }
}
