package ru.yarka.rasplibrary.handler.incoming;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import ru.yarka.rasplibrary.RASPSessionTokenManager;
import ru.yarka.rasplibrary.packet.RASPProcessedPacket;
import ru.yarka.rasplibrary.packet.RASPIncomingPacket;
import ru.yarka.rasplibrary.packet.RASPPacketPool;
import ru.yarka.rasplibrary.packet.RASPTokenizedPacket;
import ru.yarka.rasplibrary.packet.error.RASPIncorrectTokenPacket;
import ru.yarka.rasplibrary.utils.DecoderUtils;

import java.util.List;

public class PacketParser extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        byte id = buf.readByte();
        RASPIncomingPacket pk = RASPPacketPool.getPacket(id);
        if(pk != null) {
            if(pk instanceof RASPTokenizedPacket) {
                String token = DecoderUtils.readToken(buf);
                if(!RASPSessionTokenManager.sessionExists(token)) {
                    channelHandlerContext.channel().writeAndFlush(new RASPIncorrectTokenPacket());
                    return;
                }
            }

            pk.decode(buf);
            list.add(pk);
        }
        buf.readerIndex(buf.writerIndex());
    }
}
