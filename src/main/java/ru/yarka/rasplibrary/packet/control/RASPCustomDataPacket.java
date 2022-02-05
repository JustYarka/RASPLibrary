package ru.yarka.rasplibrary.packet.control;

import io.netty.buffer.ByteBuf;
import ru.yarka.rasplibrary.packet.RASPIncomingPacket;
import ru.yarka.rasplibrary.packet.RASPPacketIdetifiers;
import ru.yarka.rasplibrary.packet.RASPProcessedPacket;
import ru.yarka.rasplibrary.packet.RASPTokenizedPacket;
import ru.yarka.rasplibrary.utils.DecoderUtils;

public class RASPCustomDataPacket implements RASPIncomingPacket, RASPProcessedPacket, RASPTokenizedPacket {

    private final RASPPacketIdetifiers id = RASPPacketIdetifiers.RASP_SOUT;

    private String customData;

    @Override
    public void decode(ByteBuf in) {
        customData = DecoderUtils.readNulledString(in);
    }

    @Override
    public RASPPacketIdetifiers getId() {
        return id;
    }

    public String getCustomData() {
        return customData;
    }
}
