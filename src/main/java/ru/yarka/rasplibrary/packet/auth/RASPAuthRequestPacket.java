package ru.yarka.rasplibrary.packet.auth;

import io.netty.buffer.ByteBuf;
import ru.yarka.rasplibrary.packet.RASPProcessedPacket;
import ru.yarka.rasplibrary.packet.RASPIncomingPacket;
import ru.yarka.rasplibrary.packet.RASPPacketIdetifiers;
import ru.yarka.rasplibrary.utils.DecoderUtils;

public class RASPAuthRequestPacket implements RASPIncomingPacket {

    private final RASPPacketIdetifiers id = RASPPacketIdetifiers.RASP_AUTH_REQUEST;

    private String user;
    private String password;

    @Override
    public RASPPacketIdetifiers getId() {
        return id;
    }

    @Override
    public void decode(ByteBuf in) {
        user = DecoderUtils.readNulledString(in);
        password = DecoderUtils.readNulledString(in);
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
