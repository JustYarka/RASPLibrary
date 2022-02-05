package ru.yarka.rasplibrary;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.yarka.rasplibrary.api.IncomingPacketListener;
import ru.yarka.rasplibrary.handler.incoming.IncomingAddressFilter;
import ru.yarka.rasplibrary.handler.incoming.IncomingPacketHandler;
import ru.yarka.rasplibrary.handler.incoming.PacketFilter;
import ru.yarka.rasplibrary.handler.incoming.PacketParser;
import ru.yarka.rasplibrary.handler.outcoming.OutcomingPacketEncoder;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RASPListener {

    private final ServerBootstrap bootstrap;
    private final RASPUsesConfiguration config;
    private final List<IncomingPacketListener> listenersList;

    private RASPListener(ServerBootstrap bootstrap, RASPUsesConfiguration config, List<IncomingPacketListener> listeners) throws InterruptedException {
        this.bootstrap = bootstrap;
        this.config = config;
        this.listenersList = new ArrayList<>(listeners);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) {
                ChannelPipeline pipeline = socketChannel.pipeline();

                if(config.hasAccessList()) pipeline.addLast("checkAddress", new IncomingAddressFilter(config.getAccessList()));
                pipeline.addLast("filterPacket", new PacketFilter());
                pipeline.addLast("parsePacket", new PacketParser());
                pipeline.addLast("incomingPacketHandler", new IncomingPacketHandler(listenersList));

                pipeline.addLast("outcomingPacketEncoder", new OutcomingPacketEncoder());
            }
        });
        bootstrap.bind().sync().channel().closeFuture().sync();
    }

    public static RASPListener create(InetSocketAddress bind, RASPUsesConfiguration config, List<InetAddress> accessList) throws InterruptedException {
        return create(bind, config, accessList, Collections.emptyList());
    }

    public static RASPListener create(InetSocketAddress bind, RASPUsesConfiguration config, List<InetAddress> accessList, List<IncomingPacketListener> listeners) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());
        if(accessList != null) config.setAccessList(accessList);
        bootstrap.localAddress(bind);

        return new RASPListener(bootstrap, config, listeners);
    }

    public void stop() throws InterruptedException {
        bootstrap.config().group().shutdownGracefully().sync();
    }
}
