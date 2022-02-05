package ru.yarka.rasplibrary.packet;

public enum RASPPacketIdetifiers {
    RASP_AUTH_REQUEST(1),
    RASP_AUTH_STATUS(2),
    RASP_INCORRECT_TOKEN(3),
    RASP_SOUT(4),
    RASP_SESSION_CLOSE(5);

    private final byte numId;

    RASPPacketIdetifiers(int numId) {
        this.numId = (byte) numId;
    }

    public byte getNetworkId() {
        return numId;
    }
}
