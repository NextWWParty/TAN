package org.nextwwpart.tan.common.utils;

import java.io.*;

/**
 * 〈一句话描述功能〉<br>
 *序列化与反序列化
 * @author 18070948
 * @date: 2018/12/30 15:45
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SerializeUtil {
    public  static byte[] ObjectToBytes(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    public static Object BytesToObject(byte[] objectBytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }
    public static byte[] IntToBytes(int num)
    {
        byte[] buffer=new byte[4];
        for(int i=0;i<4;i++){
            buffer[i]=(byte)(num>>(i*8));
        }
        return buffer;
    }
    public static int BytesToInt(byte[] buf,int index)
    {
        int num=0;
        for(int i=0;i<4;i++){
            num+=(buf[index+i]&0xff)<<(i*8);
        }
        return num;
    }
}
