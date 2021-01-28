package com.icore.demo.netty.httpClient.config;

import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import lombok.Data;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;

@Data
public class NettyClientConfig {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    private String remoteHost;

    private int remotePort;

    private String protocol = "http";

    private int idleHeartbeatInterval = 8;

    private long maxWaitingReSendMillisecond = 50000;

    private long maxIdleMillisecond = 30*60*1000;

    private int maxWaitingReSendRequestNum = 5000;

    private int maxSentQueueNum = 5000;

    public NettyClientConfig(String remoteHost,int remotePort){
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    public NettyClientConfig(String remoteHost,int remotePort,String protocol){
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.protocol = protocol;
    }

    public static NettyClientConfig createByUrl(String fullUrl){
        try{
            URL url = new URL(fullUrl);
            int port = url.getPort();
            //如果端口号不存在，那么根据http或者https推断端口号
            if(port == -1){
                port = "http".equals(url.getProtocol())?80:443;
            }
            return new NettyClientConfig(url.getHost(),port,url.getProtocol());

        }catch (MalformedURLException e){
            log.error("NettyCilentConfig createByUrl url is malformed,fullUrl={}",fullUrl);
            throw new RuntimeException("url 不规范");
        }
    }

    public InetSocketAddress getRemoteAddress(){
        return new InetSocketAddress(remoteHost,remotePort);
    }

}
