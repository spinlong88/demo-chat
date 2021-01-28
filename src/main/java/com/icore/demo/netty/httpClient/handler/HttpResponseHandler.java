package com.icore.demo.netty.httpClient.handler;

import com.icore.demo.netty.httpClient.conn.NettyHttpConnection;
import com.icore.demo.netty.httpClient.vo.NettyHttpRequest;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

/**
 * 请求响应处理器
 */
public class HttpResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    private final NettyHttpConnection nettyHttpConnection;

    public HttpResponseHandler(NettyHttpConnection nettyHttpConnection){
        this.nettyHttpConnection = nettyHttpConnection;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx , FullHttpResponse fullHttpResponse) throws Exception {
        // 取出对用的request(根据http规范，同一个连接上，请求是先发送先响应。所以这里取得第一个就是这个响应对应的请求)
        NettyHttpRequest nettyHttpRequest = nettyHttpConnection.getSentQueue().poll();
        if(nettyHttpRequest!=null){
            if(NettyHttpRequest.PING != nettyHttpRequest.getType()){
                //记录，最近的活跃时间
                nettyHttpConnection.logLastActiveTime();
            }

            String result = null;
            if(fullHttpResponse.content()!=null){
                result = fullHttpResponse.content().toString(CharsetUtil.UTF_8);
            }
            log.info("traceId={}.channel={},HttpResponseHandler channelRead0 response,result={}",
                    nettyHttpRequest.getTraceId(),ctx.channel(),result);
            nettyHttpRequest.getOnResponse().accept(result,nettyHttpRequest);

        }

    }
}
