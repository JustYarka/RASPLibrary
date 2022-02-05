package ru.yarka.rasplibrary.handler.incoming;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

public class IncomingAddressFilter extends ChannelInboundHandlerAdapter {

    private final List<InetAddress> addressList;

    public IncomingAddressFilter(List<InetAddress> addressList) {
        this.addressList = addressList;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(addressList.contains(((InetSocketAddress) ctx.channel().remoteAddress()).getAddress())) super.channelRead(ctx, msg);
    }
}
