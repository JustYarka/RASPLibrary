package ru.yarka.rasplibrary.handler.incoming;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.yarka.rasplibrary.RASPSessionTokenManager;
import ru.yarka.rasplibrary.api.ConnectedPeer;
import ru.yarka.rasplibrary.api.IncomingPacketListener;
import ru.yarka.rasplibrary.packet.RASPProcessedPacket;
import ru.yarka.rasplibrary.packet.RASPIncomingPacket;
import ru.yarka.rasplibrary.packet.auth.RASPAuthRequestPacket;
import ru.yarka.rasplibrary.packet.auth.RASPAuthStatusPacket;
import ru.yarka.rasplibrary.packet.auth.RASPSessionClosePacket;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IncomingPacketHandler extends SimpleChannelInboundHandler<RASPIncomingPacket> {

    private final List<IncomingPacketListener> listeners;

    public IncomingPacketHandler(List<IncomingPacketListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RASPIncomingPacket packet) throws Exception {
        if(packet instanceof RASPProcessedPacket) {
            ConnectedPeer peer = new ConnectedPeer(channelHandlerContext.channel(), (InetSocketAddress) channelHandlerContext.channel().remoteAddress());
            listeners.forEach(listener -> listener.onPacketRead(packet, peer));
        } else if(packet instanceof RASPAuthRequestPacket) {
            RASPAuthStatusPacket pk;

            if(((RASPAuthRequestPacket) packet).getUser().equals("Yarka") && ((RASPAuthRequestPacket) packet).getPassword().equals("2010")) {
                String token = RASPSessionTokenManager.generateToken();
                pk = new RASPAuthStatusPacket(token);
            } else pk = new RASPAuthStatusPacket();

            channelHandlerContext.channel().writeAndFlush(pk);
        } else if(packet instanceof RASPSessionClosePacket) {
            System.out.printf("[%s] Session closed by remote client %s\n", new SimpleDateFormat().format(new Date()), channelHandlerContext.channel().remoteAddress());
            RASPSessionTokenManager.closeSession(((RASPSessionClosePacket) packet).getToken());
            channelHandlerContext.channel().close();
        }
    }
}
