package ru.yarka.rasplibrary.packet;

import io.netty.buffer.ByteBuf;

public interface RASPOutcomingPacket {

    void encode(ByteBuf out);

    RASPPacketIdetifiers getId();
}
