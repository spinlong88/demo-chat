package com.icore.demo.netty.httpClient;

import com.icore.demo.netty.httpClient.conn.NettyHttpConnection;
import com.icore.demo.netty.httpClient.handler.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class CustomChannelInitializer extends ChannelInitializer<Channel> {

    private final NettyHttpConnection nettyHttpConnection;

    public CustomChannelInitializer(NettyHttpConnection nettyHttpConnection){
        super();
        this.nettyHttpConnection = nettyHttpConnection;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        //触发空闲事件
        pipeline.addLast(new IdleStateHandler(0,0,nettyHttpConnection.getNettyClientConfig().getIdleHeartbeatInterval()));

        //心跳检测
        pipeline.addLast(new HeartBeatHandler(nettyHttpConnection));

        //http客户端编解码器,包括了客户端http请求编码，http响应解码
        pipeline.addLast(new HttpClientCodec());

        //把多个http请求中的数据组装成一个
        pipeline.addLast(new HttpObjectAggregator(65536));

        //用于处理大数据流
        pipeline.addLast(new ChunkedWriteHandler());

        //重连handler
        pipeline.addLast(new ReconnectHandler(nettyHttpConnection));

        //发送业务数据前，进行json编码
        pipeline.addLast(new HttpJsonRequestEncoder());

        //http请求响应处理器
        pipeline.addLast(new HttpResponseHandler(nettyHttpConnection));

        //异常处理handler
        pipeline.addLast(new ExceptionHandler());

    }
}
