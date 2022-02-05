package ru.yarka.rasplibrary.utils;

import io.netty.buffer.ByteBuf;

public class DecoderUtils {

    public static String readToken(ByteBuf buf) {
        StringBuilder builder = new StringBuilder();
        byte b;
        int len = 0;
        while(buf.readableBytes() > 0 && len++ <= 48 && (b = buf.readByte()) != '\0') {
            builder.append((char) b);
        }
        buf.readByte(); // read null byte
        return builder.toString();
    }

    public static String readNulledString(ByteBuf buf) {
        StringBuilder builder = new StringBuilder();
        byte b;
        while(buf.readableBytes() > 0 && (b = buf.readByte()) != '\0') {
            builder.append((char) b);
        }
        return builder.toString();
    }
}
