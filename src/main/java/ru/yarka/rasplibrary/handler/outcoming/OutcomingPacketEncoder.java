package ru.yarka.rasplibrary.handler.outcoming;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.yarka.rasplibrary.handler.incoming.PacketFilter;
import ru.yarka.rasplibrary.packet.RASPOutcomingPacket;

public class OutcomingPacketEncoder extends MessageToByteEncoder<RASPOutcomingPacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RASPOutcomingPacket raspOutcomingPacket, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(PacketFilter.SIGNATURE);
        byteBuf.writeByte(raspOutcomingPacket.getId().getNetworkId());
        raspOutcomingPacket.encode(byteBuf);
    }
}
