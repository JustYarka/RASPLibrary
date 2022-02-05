package ru.yarka.rasplibrary.api;

import ru.yarka.rasplibrary.packet.RASPIncomingPacket;

public interface IncomingPacketListener {

    void onPacketRead(RASPIncomingPacket packet, ConnectedPeer peer);
}
