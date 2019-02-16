package org.nextwwpart.tan.common.portTransform;

import org.nextwwpart.tan.common.utils.SerializeUtil;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class PortTransformReceiveThread extends Thread {
    private int packageSize = 1024;
    private int transformPort = 15645;
    private int infoPort = 15646;
    private Map<String, PortTransformRelation> relationMap = new HashMap<>();
    private boolean exitFlag = true;

    public void setTransformPort(int transformPort) {
        this.transformPort = transformPort;
    }

    public void setInfoPort(int infoPort) {
        this.infoPort = infoPort;
    }

    public void setPackageSize(int packageSize) {
        this.packageSize = packageSize;
    }

    Runnable receiveInfoRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                DatagramSocket datagramSocket = new DatagramSocket(infoPort, InetAddress.getByName("0.0.0.0"));
                while (exitFlag) {
                    DatagramPacket re = new DatagramPacket(new byte[packageSize], packageSize);
                    datagramSocket.receive(re);
                    PortTransformRelation portTransformRelation = (PortTransformRelation) SerializeUtil.BytesToObject(re.getData());
                    String key = portTransformRelation.getSourceIP() + ":" + portTransformRelation.getSourcePort();
                    relationMap.put(key, portTransformRelation);
                    byte[] ack = "ACK".getBytes();
                    DatagramPacket ackPacket = new DatagramPacket(ack, ack.length, re.getAddress(), re.getPort());
                    datagramSocket.send(ackPacket);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };
    Runnable transformRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(transformPort);
                while (exitFlag) {
                    Socket sourceSocket = serverSocket.accept();
                    String key = sourceSocket.getInetAddress() + ":" + sourceSocket.getPort();
                    PortTransformRelation portTransformRelation = relationMap.remove(key);
                    if (portTransformRelation != null) {
                        Socket aimSocket = new Socket(portTransformRelation.getAimIP(), portTransformRelation.getAimPort());
                        new Thread(new ReadWriteRunnable(sourceSocket, aimSocket)).start();
                        new Thread(new ReadWriteRunnable(aimSocket, sourceSocket)).start();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    public void run() {
        new Thread(receiveInfoRunnable).start();
        new Thread(transformRunnable).start();
    }
}
