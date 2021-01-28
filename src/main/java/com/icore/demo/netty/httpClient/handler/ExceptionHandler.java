package com.icore.demo.netty.httpClient.handler;

import com.icore.util.LogPrintUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

public class ExceptionHandler extends ChannelDuplexHandler {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    /**
     * inboundHandler 的异常才会传递到这个方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception{
        log.error("ExceptionHandler exceptionCaught channel={},cause={}",ctx.channel(), LogPrintUtil.logExceptionTack(cause));
    }

    /**
     * 捕获outboundHandler的write异常
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)throws Exception{
        ctx.write(msg,promise.addListener(future -> {
            if(!future.isSuccess()){
                Throwable cause = future.cause();
                if(cause!=null){
                    log.error("ExceptionHandler write error ,channel={},msg={},cause={}",
                            ctx.channel(),msg,LogPrintUtil.logExceptionTack(cause));
                }
            }
        }));
    }

    /**
     * 捕获outboundHandler的connect异常
     */
    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,SocketAddress localAddress,ChannelPromise promise)throws Exception{
        ctx.connect(remoteAddress,localAddress,promise.addListener(future -> {
            if(!future.isSuccess()){
                Throwable cause = future.cause();
                if(cause!=null){
                    log.error("ExceptionHandler connect error ,channel={},remoteAddress={},localAddress={}.cause={}",
                            ctx.channel(),remoteAddress,localAddress,LogPrintUtil.logExceptionTack(cause));
                }
            }
        }));
    }


}
