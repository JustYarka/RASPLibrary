package ru.yarka.rasplibrary.api;

import io.netty.channel.Channel;
import ru.yarka.rasplibrary.packet.RASPOutcomingPacket;

import java.net.InetSocketAddress;

public class ConnectedPeer {

    private final Channel channel;
    private final InetSocketAddress address;

    public ConnectedPeer(Channel channel, InetSocketAddress address) {
        this.channel = channel;
        this.address = address;
    }

    public void sendPacket(RASPOutcomingPacket packet) {
        channel.writeAndFlush(packet);
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}
