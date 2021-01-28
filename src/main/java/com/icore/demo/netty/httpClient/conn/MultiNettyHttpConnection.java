package com.icore.demo.netty.httpClient.conn;

import com.icore.demo.netty.httpClient.config.NettyClientConfig;
import com.icore.demo.netty.httpClient.executor.NettyClientCloseExecutor;
import com.icore.demo.netty.httpClient.executor.NettyClientConnectExecutor;
import com.icore.demo.netty.httpClient.factory.NettyHttpConnectionFactory;
import com.icore.demo.netty.httpClient.vo.NettyHttpRequest;
import io.netty.util.internal.SystemPropertyUtil;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * 多个nettyHttpConnection构成环
 */
public class MultiNettyHttpConnection {

    private static final int CONNECTION_NUM = SystemPropertyUtil.getInt("netty.http.connection.num",2);

    private static ExecutorService connectExecutor = NettyClientConnectExecutor.getConnectExecutor();

    private static ExecutorService closeExecutor = NettyClientCloseExecutor.getCloseExecutor();

    private final NettyHttpConnection[] nettyHttpConnections;

    private final int connectionNum;

    public MultiNettyHttpConnection(NettyClientConfig nettyClientConfig){
        this(CONNECTION_NUM,nettyClientConfig);
    }

    public MultiNettyHttpConnection(int connectionNum,NettyClientConfig nettyClientConfig){
        this(connectionNum,new NettyHttpConnectionFactory(nettyClientConfig));
    }

    public MultiNettyHttpConnection(int connectionNum,NettyHttpConnectionFactory factory){
        if(connectionNum<2){
            throw new IllegalStateException("connectionNum<2");
        }

        this.connectionNum = connectionNum;
        NettyHttpConnection first = factory.createConnection();
        NettyHttpConnection p = first;

        //让这些链接形成一个环
        nettyHttpConnections = new NettyHttpConnection[connectionNum];
        nettyHttpConnections[0] = p;
        for(int i=1;i<connectionNum;i++){
            nettyHttpConnections[i] = p.next = factory.createConnection();
            p = p.next;
        }
        p.next = first;
    }

    /**
     * 随机选择一个链接，发送请求
     */
    public void doHttpRequest(NettyHttpRequest nettyHttpRequest){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int i = random.nextInt(connectionNum);
        nettyHttpConnections[i].doHttpRequest(nettyHttpRequest);
    }

    /**
     * 与服务端建立链接
     */
    public void connect(){
        //同时发起多个connect请求
        List<CompletableFuture<Void>> connectFuture = Arrays.stream(nettyHttpConnections)
                .map(nettyHttpConnection -> CompletableFuture.runAsync(nettyHttpConnection::connect,connectExecutor))
                .collect(Collectors.toList());
        //阻塞灯带
        connectFuture.forEach(CompletableFuture::join);
    }

    /**
     * 关闭链接
     */
    public void close(){
        //同时发起多个close
        Arrays.stream(nettyHttpConnections)
                .forEach(nettyHttpConnection -> CompletableFuture.runAsync(nettyHttpConnection::close,closeExecutor));
    }

    /**
     * 是否所有链接都空闲
     */
    public boolean isAllIdle(){
        return Arrays.stream(nettyHttpConnections).allMatch(NettyHttpConnection::isIdle);
    }


}
