package ru.yarka.rasplibrary.handler.incoming;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Arrays;
import java.util.List;

public class PacketFilter extends ByteToMessageDecoder {

    public static final byte[] SIGNATURE = {(byte) 0xFA, (byte) 0xDD};

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        if(byteBuf.readableBytes() < 4) return;

        byte[] prefix = new byte[2];

        ByteBuf bufCopy = byteBuf.copy();
        bufCopy.readBytes(prefix);

        byteBuf.readerIndex(byteBuf.writerIndex());

        if(Arrays.equals(prefix, SIGNATURE)) list.add(bufCopy);
    }
}
