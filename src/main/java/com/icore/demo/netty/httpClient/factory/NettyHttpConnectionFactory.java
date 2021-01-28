package com.icore.demo.netty.httpClient.factory;

import com.icore.demo.netty.httpClient.config.NettyClientConfig;
import com.icore.demo.netty.httpClient.conn.NettyHttpConnection;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyHttpConnectionFactory {

    private final EventLoopGroup group;

    private NettyClientConfig config;

    private Bootstrap bootstrap;

    public NettyHttpConnectionFactory(NettyClientConfig nettyClientConfig){
        this.group = NettyEventLoopGroupFactory.eventLoopGroup(Math.min(Runtime.getRuntime().availableProcessors(),16),"NettyClientWorker");
        this.config = nettyClientConfig;
        createBootstrap();
    }

    public void createBootstrap(){
        bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(group)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .remoteAddress(config.getRemoteAddress());
    }

    public NettyHttpConnection createConnection(){
        return new NettyHttpConnection(bootstrap.clone(),config);
    }

}
