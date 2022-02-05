package ru.yarka.rasplibrary.packet.error;

import io.netty.buffer.ByteBuf;
import ru.yarka.rasplibrary.packet.RASPOutcomingPacket;
import ru.yarka.rasplibrary.packet.RASPPacketIdetifiers;

public class RASPIncorrectTokenPacket implements RASPOutcomingPacket {

    private final RASPPacketIdetifiers id = RASPPacketIdetifiers.RASP_INCORRECT_TOKEN;

    @Override
    public void encode(ByteBuf out) {

    }

    @Override
    public RASPPacketIdetifiers getId() {
        return id;
    }
}
