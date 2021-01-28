package com.icore.demo.netty.httpClient.factory;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * 用于创建带线程名称的eventLoopGroup
 */
public class NettyEventLoopGroupFactory {

    public static EventLoopGroup eventLoopGroup(int threads,String threadFactoryName){
        ThreadFactory threadFactory = new DefaultThreadFactory(threadFactoryName,true);
        return new NioEventLoopGroup(threads,threadFactory);
    }

}
