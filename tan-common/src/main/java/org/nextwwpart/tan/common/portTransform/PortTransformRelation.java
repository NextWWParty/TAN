package org.nextwwpart.tan.common.portTransform;

import java.io.Serializable;

public class PortTransformRelation implements Serializable {
    private String sourceIP;
    private int sourcePort;
    private String aimIP;
    private int aimPort;

    public String getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getAimIP() {
        return aimIP;
    }

    public void setAimIP(String aimIP) {
        this.aimIP = aimIP;
    }

    public int getAimPort() {
        return aimPort;
    }

    public void setAimPort(int aimPort) {
        this.aimPort = aimPort;
    }
}
