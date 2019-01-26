package org.nextwwpart.tan.common.utils;

import org.nextwwpart.tan.common.classLoader.ClassToBytes;
import org.nextwwpart.tan.common.classLoader.WebClassLoader;

import java.io.IOException;
import java.net.Socket;

/**
 * @author wuyunxiao
 * 注：不同系统下的回车不同，windows:\r\n  unix:\n mac:\r 导致字节码不一致还需转换
 */
public class ClassSenderUtil {
    public static void sendClass(Socket socket, Class sendClass) throws IOException {
        String dealThreadName = sendClass.getName();
        socket.getOutputStream().write(SerializeUtil.IntToBytes(dealThreadName.length()));
        byte[] buffer = dealThreadName.getBytes();
        socket.getOutputStream().write(buffer);
        String root = sendClass.getResource("").getPath();
        byte[] classData = ClassToBytes.loadClassData(sendClass.getSimpleName(), root);
        if (classData != null) {
            socket.getOutputStream().write(SerializeUtil.IntToBytes(classData.length));
            socket.getOutputStream().write(classData);
        }
    }

    public static Class receiveClass(Socket socket) throws IOException, ClassNotFoundException {
        byte[] bufferSizebytes = new byte[4];
        socket.getInputStream().read(bufferSizebytes);
        int bufferSize = SerializeUtil.BytesToInt(bufferSizebytes, 0);
        byte[] strBuffer = new byte[bufferSize];
        socket.getInputStream().read(strBuffer);
        String dealThreadName = new String(strBuffer);
        //最好保存一下线程实例，不必每次都通过反射构建对象
        socket.getInputStream().read(bufferSizebytes);
        bufferSize = SerializeUtil.BytesToInt(bufferSizebytes, 0);
        byte[] classDataBuffer = new byte[bufferSize];
        socket.getInputStream().read(classDataBuffer);
        WebClassLoader webClassLoader = new WebClassLoader(classDataBuffer);
        return webClassLoader.loadClass(dealThreadName);
    }
}
