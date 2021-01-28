package com.icore.demo.netty.httpClient.handler;

import com.icore.demo.netty.httpClient.conn.NettyHttpConnection;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ReconnectHandler extends ChannelInboundHandlerAdapter {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    private final NettyHttpConnection nettyHttpConnection;

    public ReconnectHandler(NettyHttpConnection nettyHttpConnection){
        this.nettyHttpConnection = nettyHttpConnection;
    }


    public void channelInactive(ChannelHandlerContext ctx)throws Exception{
        log.info("ReconnectionHandler channelInactive ,channel={},currentThread={}",
                ctx.channel(),Thread.currentThread().getName());
        nettyHttpConnection.onClosed(nettyHttpConnection.getSentQueue());
        ctx.fireChannelInactive();
    }

}
