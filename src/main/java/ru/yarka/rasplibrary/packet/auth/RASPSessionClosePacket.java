package ru.yarka.rasplibrary.packet.auth;

import io.netty.buffer.ByteBuf;
import ru.yarka.rasplibrary.packet.RASPIncomingPacket;
import ru.yarka.rasplibrary.packet.RASPPacketIdetifiers;
import ru.yarka.rasplibrary.packet.RASPTokenizedPacket;
import ru.yarka.rasplibrary.utils.DecoderUtils;

public class RASPSessionClosePacket implements RASPIncomingPacket, RASPTokenizedPacket {

    private final RASPPacketIdetifiers id = RASPPacketIdetifiers.RASP_SESSION_CLOSE;

    private String token;

    @Override
    public void decode(ByteBuf in) {
        token = DecoderUtils.readToken(in);
    }

    @Override
    public RASPPacketIdetifiers getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
