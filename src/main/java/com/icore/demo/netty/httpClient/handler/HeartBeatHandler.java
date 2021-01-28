package com.icore.demo.netty.httpClient.handler;

import com.icore.demo.netty.httpClient.conn.NettyHttpConnection;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 心跳检测handler
 * IdleStateHandler会触发HeartBeatHandle的userEventTriggered方法
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    private final NettyHttpConnection nettyHttpConnection;

    public HeartBeatHandler(NettyHttpConnection nettyHttpConnection){
        this.nettyHttpConnection = nettyHttpConnection;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx,Object evt)throws Exception{
        if(evt instanceof IdleStateEvent){
            sendPing();
        }else{
            ctx.fireUserEventTriggered(evt);
        }
    }

    private void sendPing(){
        String remoteHost = nettyHttpConnection.getNettyClientConfig().getRemoteHost();
        Integer remotePort = nettyHttpConnection.getNettyClientConfig().getRemotePort();
        String protocol = nettyHttpConnection.getNettyClientConfig().getProtocol();
    }




}
