package com.icore.demo.netty.httpClient.factory;

import com.icore.demo.netty.httpClient.client.LongConnectionNettyClient;
import com.icore.demo.netty.httpClient.config.NettyClientConfig;
import com.icore.demo.netty.httpClient.util.SegmentLock;
import io.netty.util.HashedWheelTimer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class LongConnectionNettyClientFactory {

    /**
     * 定时清理空闲链接
     */
    private static final HashedWheelTimer IDLE_SWEEP_TIMER = new HashedWheelTimer();

    private static ConcurrentHashMap<String, LongConnectionNettyClient> NETTY_CLIENT_MAP = new ConcurrentHashMap(1024);

    private static final SegmentLock LOCK = new SegmentLock<String>();

    static {
        scheduleSweepIdleConnection();
    }

    public static LongConnectionNettyClient getOrCreateLongConnectionNettyClient(String url){
        NettyClientConfig nettyClientConfig = NettyClientConfig.createByUrl(url);
        return getOrCreateLongConnectionNettyClient(nettyClientConfig);
    }

    public static LongConnectionNettyClient getOrCreateLongConnectionNettyClient(NettyClientConfig nettyClientConfig){
        String nodeKey = getNodeKey(nettyClientConfig);
        LongConnectionNettyClient longConnectionNettyClient = NETTY_CLIENT_MAP.get(nodeKey);
        if(longConnectionNettyClient!=null){
            return longConnectionNettyClient;
        }
        LOCK.lock(nodeKey);
        try{
            LongConnectionNettyClient exitedNettyClient = NETTY_CLIENT_MAP.get(nodeKey);
            if(exitedNettyClient!=null){
                return exitedNettyClient;
            }
            LongConnectionNettyClient newClient = new LongConnectionNettyClient(nettyClientConfig);
            NETTY_CLIENT_MAP.put(nodeKey,newClient);
            return newClient;
        }finally {
            LOCK.unlock(nodeKey);
        }
    }

    private static String getNodeKey(NettyClientConfig nettyClientConfig){
        return nettyClientConfig.getRemoteHost()+":"+nettyClientConfig.getRemotePort();
    }

    /**
     * 定时清理空闲链接
     */
    private static void scheduleSweepIdleConnection(){
        IDLE_SWEEP_TIMER.newTimeout(task->{
            if(!task.isCancelled()){
                sweepIdleConnection();
            }

        },10, TimeUnit.SECONDS);
    }

    /**
     * 清理空闲链接
     */
    private static void sweepIdleConnection(){
        try{
            if(!NETTY_CLIENT_MAP.isEmpty()){
                for(Map.Entry<String,LongConnectionNettyClient> entry:NETTY_CLIENT_MAP.entrySet()){
                    String nodeKey = entry.getKey();
                    LongConnectionNettyClient nettyClient = entry.getValue();
                    //如果客户端太空闲了，那么关闭它，释放链接，并且从map中移除掉
                    if(nettyClient.isIdle()){
                        NETTY_CLIENT_MAP.remove(nodeKey);
                        nettyClient.close();
                    }
                }
            }


        }finally {
            scheduleSweepIdleConnection();
        }

    }

}
