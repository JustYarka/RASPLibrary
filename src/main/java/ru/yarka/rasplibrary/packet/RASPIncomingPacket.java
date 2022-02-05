package ru.yarka.rasplibrary.packet;

import io.netty.buffer.ByteBuf;

public interface RASPIncomingPacket {

    void decode(ByteBuf in);

    RASPPacketIdetifiers getId();
}
