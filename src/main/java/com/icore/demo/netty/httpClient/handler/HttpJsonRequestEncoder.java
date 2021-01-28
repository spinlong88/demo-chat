package com.icore.demo.netty.httpClient.handler;

import com.icore.demo.netty.httpClient.exception.RpcException;
import com.icore.demo.netty.httpClient.vo.NettyHttpRequest;
import com.icore.util.FastJsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.*;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;

public class HttpJsonRequestEncoder extends MessageToMessageEncoder<NettyHttpRequest> {

    private final static String CHARSET_NAME = "UTF-8";

    private final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

    protected void encode(ChannelHandlerContext ctx, NettyHttpRequest nettyHttpRequest, List<Object> out){

        FullHttpRequest request = null;
        if(nettyHttpRequest.getHttpMethod() == HttpMethod.POST){
            String body = FastJsonUtil.toJSON(nettyHttpRequest.getBody());

            ByteBuf encodeBuf = Unpooled.copiedBuffer(body,UTF_8);
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,nettyHttpRequest.getPath(),encodeBuf);

            HttpUtil.setContentLength(request,encodeBuf.readableBytes());

        }else if(nettyHttpRequest.getHttpMethod() == HttpMethod.GET){
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,nettyHttpRequest.getPath());
        }else{
            nettyHttpRequest.getOnError().accept(new RpcException("HttpMethod is not supported"),nettyHttpRequest);
            return;
        }
        //设置请求头
        populateHeaders(request,nettyHttpRequest,ctx);

        out.add(request);
    }

    /**
     * 设置请求头
     */
    private void populateHeaders(FullHttpRequest request,NettyHttpRequest nettyHttpRequest,ChannelHandlerContext ctx){
        HttpHeaders headers = request.headers();

        InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();
        String host = socketAddress.getHostName()+":"+socketAddress.getPort();

        headers.set(HttpHeaderNames.HOST,host);
        headers.set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        if(nettyHttpRequest.getHeaderMap() !=null && !nettyHttpRequest.getHeaderMap().isEmpty()){
            nettyHttpRequest.getHeaderMap().forEach(headers::set);
        }

    }



}
