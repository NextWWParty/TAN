package org.nextwwpart.tan.common.portTransform;

import java.net.*;

public class PortTransform {
    private static int PACKAGE_SIZE=1024;
    private int transformPort = 15645;
    private int infoPort = 15646;
    private String transformIP = "127.0.0.1";

    public void setTransformPort(int transformPort) {
        this.transformPort = transformPort;
    }

    public void setInfoPort(int infoPort) {
        this.infoPort = infoPort;
    }

    public void setTransformIP(String transformIP) {
        this.transformIP = transformIP;
    }

    public void beginReceive() {
        PortTransformReceiveThread portTransformReceiveThread = new PortTransformReceiveThread();
        portTransformReceiveThread.setInfoPort(infoPort);
        portTransformReceiveThread.setPackageSize(PACKAGE_SIZE);
        portTransformReceiveThread.setTransformPort(transformPort);
        portTransformReceiveThread.start();
    }

    public Socket sendToTransform() throws UnknownHostException, SocketException {
        DatagramSocket datagramSocket = new DatagramSocket();
        DatagramPacket sendPackage = new DatagramPacket(new byte[PACKAGE_SIZE], PACKAGE_SIZE, InetAddress.getByName(transformIP), infoPort);

        return new Socket();
    }
}
