package ru.yarka.rasplibrary.packet.auth;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import ru.yarka.rasplibrary.packet.RASPOutcomingPacket;
import ru.yarka.rasplibrary.packet.RASPPacketIdetifiers;

public class RASPAuthStatusPacket implements RASPOutcomingPacket {

    private final RASPPacketIdetifiers id = RASPPacketIdetifiers.RASP_AUTH_STATUS;

    public static final byte AUTH_STATUS_SUCCESS = 0;
    public static final byte AUTH_STATUS_FAILURE = 1;

    private byte status = AUTH_STATUS_FAILURE;
    private String token;

    public RASPAuthStatusPacket(String token) {
        this.status = AUTH_STATUS_SUCCESS;
        this.token = token + '\0';
    }

    public RASPAuthStatusPacket() {
    }

    public byte getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    @Override
    public RASPPacketIdetifiers getId() {
        return id;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(status);

        if(status == AUTH_STATUS_SUCCESS) ByteBufUtil.writeAscii(out, token);
    }
}
