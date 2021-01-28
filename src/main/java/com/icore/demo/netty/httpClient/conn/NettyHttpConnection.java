package com.icore.demo.netty.httpClient.conn;

import com.icore.demo.netty.httpClient.CustomChannelInitializer;
import com.icore.demo.netty.httpClient.config.NettyClientConfig;
import com.icore.demo.netty.httpClient.exception.RpcException;
import com.icore.demo.netty.httpClient.executor.NettyConnectionChannelWriteExecutor;
import com.icore.demo.netty.httpClient.executor.NettyConnectionTransferSentRequestExecutor;
import com.icore.demo.netty.httpClient.vo.NettyHttpRequest;
import com.icore.util.LogPrintUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Data;
import java.nio.channels.ClosedChannelException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.icore.demo.netty.httpClient.vo.NettyHttpRequest.PING;

@Data
public class NettyHttpConnection {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    /**
     * 请求在NettyHttpConnection之间最大的流转次数
     */
    private static final int MAX_TRANSFER_NUM_BETWEEN_CLIENT = 6;

    private static final long CONNECTED = -2;

    /**
     * netty client配置顶
     */
    private NettyClientConfig nettyClientConfig;

    /**
     * 当前使用的channel
     */
    private volatile Channel channel;

    NettyHttpConnection next;

    /**
     * 链接最近一次关闭的时间
     */
    private long lastCloseTime = -1;

    /**
     * 是否客户端主动断开链接(超过最大空闲时间，被自动清理)
     */
    private volatile boolean activeClose = false;

    /**
     * 最大的等待重发时间，超过此时间，等待重发队列中的请求将会被清楚并返回错误
     */
    private final long maxWaittingReSendMillisecond;

    /**
     * 最大空闲时间，超过此时间，链接将自动断开
     */
    private final long maxIdleMillisecond;

    /**
     * 最近活跃的时间(最近一次发送请求或者接收结果的时间)
     */
    private volatile long lastActiveTime;

    /**
     * 等待重发队列
     */
    private LinkedBlockingDeque<NettyHttpRequest> waittingReSendQueue;

    /**
     * 请求已发送，灯带返回结果队列
     */
    private LinkedBlockingDeque<NettyHttpRequest> sentQueue;

    /**
     * 调用 channal writeAndFlush的单例线程池
     */
    private ExecutorService channelWriteExecutoer = NettyConnectionChannelWriteExecutor.getChannelWriteExecutor();

    /**
     * 搬运已发送请求 单例线程池
     */
    private ExecutorService transferSentRequestExecutoer = NettyConnectionTransferSentRequestExecutor.getTransferSendRequestExecutor();

    /**
     * 是否连接成功
     */
    private boolean bIsConnectionOk;

    /**
     * 重连的future
     */
    private ScheduledFuture<?> scheduledFuture;

    private Bootstrap bootstrap;

    public NettyHttpConnection(Bootstrap bootstrap,NettyClientConfig nettyClientConfig){

        this.nettyClientConfig = nettyClientConfig;
        this.maxWaittingReSendMillisecond = nettyClientConfig.getMaxWaitingReSendMillisecond();
        this.maxIdleMillisecond = nettyClientConfig.getMaxIdleMillisecond();
        this.waittingReSendQueue = new LinkedBlockingDeque<>((nettyClientConfig.getMaxWaitingReSendRequestNum()));
        this.sentQueue = new LinkedBlockingDeque<>((nettyClientConfig.getMaxSentQueueNum()));
        bootstrap.handler(new CustomChannelInitializer(this));
        this.bootstrap = bootstrap;
    }

    public void doHttpRequest(NettyHttpRequest nettyHttpRequest){
        doHttpRequest(nettyHttpRequest,0);
    }

    private void doHttpRequest(NettyHttpRequest nettyHttpRequest,int transferNumBetweenClient){
        System.out.println("bIsConnectionOk!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+bIsConnectionOk);
        //如果连接失败
        if(!bIsConnectionOk){
            //如果是客户端主动关闭
            if(activeClose){
                nettyHttpRequest.getOnError().accept(new RpcException("user close con"),nettyHttpRequest);
                return;
            }
            //如果从来没有连接过
            if(lastCloseTime<0){
                nettyHttpRequest.getOnError().accept(new RpcException("closed con"),nettyHttpRequest);
                return;
            }

            if(transferNumBetweenClient< MAX_TRANSFER_NUM_BETWEEN_CLIENT){
                next.doHttpRequest(nettyHttpRequest,transferNumBetweenClient+1);
                return;
            }

            long now = System.currentTimeMillis();
            System.out.println(now - lastCloseTime>maxWaittingReSendMillisecond);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@11111");
            if(lastCloseTime>0 && now - lastCloseTime>maxWaittingReSendMillisecond){
                nettyHttpRequest.getOnError().accept(new RpcException("closed con"),nettyHttpRequest);
                return;
            }

            boolean offerSuccess = waittingReSendQueue.offer(nettyHttpRequest);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22222");
            //队列满了
            if(!offerSuccess){
                nettyHttpRequest.getOnError().accept(new RpcException("too many request waitting closed con"),nettyHttpRequest);
                return;
            }
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@33333");

        }else{
            //如果连接成功,那么去发送
            doWriteO(this.channel,nettyHttpRequest);
        }
    }

    private void doWriteO(Channel channel,NettyHttpRequest nettyHttpRequest){
        if(channel == null || !channel.isActive()){

            nettyHttpRequest.getOnError().accept(new RpcException("chnnel is not active"),nettyHttpRequest);
            return;
        }

        if(PING!=nettyHttpRequest.getType()){
            //记录 最近活跃的时间
            logLastActiveTime();
        }

        channelWriteExecutoer.submit(()->{
            //加入到已发送队列，如果失败则抛异常
            if(!sentQueue.offer(nettyHttpRequest)){
                nettyHttpRequest.getOnError().accept(new RpcException("too many request waitting receive"),nettyHttpRequest);
                return;
            }

            if(channel == null || !channel.isActive()){
                sentQueue.remove(nettyHttpRequest);
                nettyHttpRequest.getOnError().accept(new RpcException("channel is not active"),nettyHttpRequest);
                return;
            }

            //发送
            ChannelFuture channelFuture = channel.writeAndFlush(nettyHttpRequest);
            //监听发送情况
            channelFuture.addListener(f->{
                //如果发送成功，那么打印一行日志
                if(f.isSuccess()){
                    log.info("request send success");
                    return;
                }

                //日桂平发送是吧，那么先从已发送队列移除这个请求
                sentQueue.remove(nettyHttpRequest);
                //再决定是移交给其他链接发送，还是抛异常
                Throwable cause = f.cause();
                if(cause instanceof ClosedChannelException){
                    //调用了write,会在netty的缓冲区排队等待发送。如果排队过程中链接断开了，那么将请求交给下一个链接发送
                    next.doHttpRequest(nettyHttpRequest,0);
                }else if(cause!=null){
                    nettyHttpRequest.getOnError().accept(new RpcException("send error"),nettyHttpRequest);
                    return;
                }else{
                    nettyHttpRequest.getOnError().accept(new RpcException("send occur unknown error"),nettyHttpRequest);
                    return;
                }
            });
        });
    }

    public void connect(){
        doConnect(false);
    }

    private void doConnect(boolean asyncAndReconnect){
        log.info(" NettyHttpConnection doConnect");
        if(activeClose){
            throw new IllegalStateException("user closed");
        }

        ChannelFuture channelFuture = bootstrap.connect();
        //如果是异步重连
        if(asyncAndReconnect){
            channelFuture.addListener(f->{
               //如果连接成功
               if(f.isSuccess()){
                   //链接建立之后的一些操作
                   onOpened(channelFuture,channel);
                   return;
               }

               //如果链接失败
               System.out.println("close!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
               bIsConnectionOk = false;
               Throwable cause = f.cause();
               String errorMsg = cause!=null?cause.getMessage():"unknow error";
               log.error("connect error with conf={},cause={}",nettyClientConfig,errorMsg);
               long now = System.currentTimeMillis();

               //如果是客户端主动关闭的，那么也把保存在队列中的请求全部返回错误，并清空队列
                if(activeClose){
                    RpcException rpcException = new RpcException(" waitting util user closer con");
                    requestErrorAfterConnectClosed(rpcException);
                    return;
                }

                if(lastCloseTime>0&& now-lastCloseTime>maxWaittingReSendMillisecond){
                    RpcException rpcException = new RpcException(" colsed con and recon failed "+errorMsg);
                    requestErrorAfterConnectClosed(rpcException);
                }
                //如果重连没成功，那么创建 future ，延迟2秒在重连
                scheduledFuture = channelFuture.channel().eventLoop().schedule(new Runnable() {
                    @Override
                    public void run() {
                        log.info(" scheduleReconnect doConnect ");
                        doConnect(true);
                    }
                },2, TimeUnit.SECONDS);
                log.info(" schedule a task to reconnect ");
            });
        }else{
            //如果是同步重连
            channelFuture.awaitUninterruptibly();

            //如果链接成功
            if(channelFuture.isSuccess()){
                //链接建立之后的一些操作
                onOpened(channelFuture,channel);
                return;
            }

            //如果链接失败
            System.out.println("close!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            bIsConnectionOk = false;
            Throwable cause = channelFuture.cause();
            if(cause!=null){
                throw new RpcException("connect failed with conf "+nettyClientConfig,cause);
            }else{
                throw new RpcException(" unknow reason connect failed with conf "+nettyClientConfig);
            }
        }
    }

    /**
     * 清理旧的channel和取消重连futue
     */
    private void cleanOldChannelAndCancelReconnect(ChannelFuture future,Channel oldChannel){
        //记录 最近活跃的时间
        logLastActiveTime();

        try{
            if(oldChannel!=null){
                try {
                    log.info(" close old netty channel={}",oldChannel);
                    oldChannel.close();
                }catch (Exception e){
                    log.error(" close old netty error={}", LogPrintUtil.logExceptionTack(e));
                }
            }

        }finally {
            //新的channel覆盖field
            this.channel = future.channel();
            System.out.println("open!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            this.bIsConnectionOk = true;
            log.info(" connection is ok, new channel={}",this.channel);
            //取消重连future
            if(this.scheduledFuture!=null){
                log.info(" cancel scheduledFuture");
                this.scheduledFuture.cancel(true);
            }
        }
    }

    /**
     * 客户端主动关闭链接
     */
    public void close(){

        activeClose = true;

        //关channel
        Channel channel = this.channel;
        if(channel!=null){
            try{
                this.channel = null;
                log.info(" close netty channel={}",channel);
                channel.close();
            }catch (Exception e){
                log.error(" close netty channel error={}", LogPrintUtil.logExceptionTack(e));
            }
        }

        //线程关闭
        channelWriteExecutoer.shutdown();
        transferSentRequestExecutoer.shutdown();

        //取消重连future
        if(this.scheduledFuture !=null){
            log.info(" cancel scheduledFuture");
            this.scheduledFuture.cancel(true);
        }
        //链接关闭后，把保存在队列中的请求全部返回错误，并清空队列
        RpcException rpcException = new RpcException(" waitting util user closer connection ");
        requestErrorAfterConnectClosed(rpcException);
    }

    /**
     * 链接建立之后的一些操作
     */
    public void onOpened(ChannelFuture channelFuture,Channel oldChannel){
        //如果是客户端你主动关闭了链接，那么关闭链接，把报错在队里中的请求全部返回错误，并清空队列
        if(activeClose){

            //关闭新的链接
            if(channelFuture.channel()!=null){
                try{
                    channelFuture.channel().close();
                }catch (Exception e){
                    log.error(" close netty channel error={}", LogPrintUtil.logExceptionTack(e));
                }
            }
            RpcException rpcException = new RpcException(" waitting util user closer connection ");
            requestErrorAfterConnectClosed(rpcException);
            return;
        }

        //如果是正常链接上

        //清理旧的channel和取消重连future
        cleanOldChannelAndCancelReconnect(channelFuture,oldChannel);

        //在链接建立之后，把保存在队列中的请求全部发送出去，并清空队列
        requestWriteAfterConnected(channelFuture.channel());
    }

    /**
     * 链接关闭(非客户端主动关闭)后
     * 已经发送出去灯带响应的那些请求，会被服务端丢弃，所以要保存这些请求，等待重发
     * 没有发送出去，在netty客户端缓冲区的那些请求，会收到ClosedChannelException异常，这个异常在Netty doWriteO方法被处理，
     */
    public void onClosed(LinkedBlockingDeque<NettyHttpRequest> sendQueue){
        lastCloseTime = System.currentTimeMillis();
        channel = null;
        System.out.println("close!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        bIsConnectionOk = false;

        if(activeClose){
            return;
        }

        if(sendQueue.size()!=0){
            //将sentQueue的数据全部转移到sentL
            LinkedList<NettyHttpRequest> sentList= new LinkedList<>();
            sendQueue.drainTo(sentList);

            transferSentRequestExecutoer.submit(()->{
               NettyHttpConnection next = this.next;
               //如果没有下一个链接了，就保存这些请求到等待重发队列
                if(next ==null || next ==this){
                    sentList.forEach(x->{
                        //插入到重发队列失败，那么返回异常
                        if(!waittingReSendQueue.offer(x)){
                            x.getOnError().accept(new RpcException(" too many request waitting closed con"),x);
                        }
                    });
                }else{
                    //如果有下一个链接，那么把这些请求转移到其他链接，重新发送到服务端.
                    for(NettyHttpRequest nettyHttpRequest:sentList){
                        next.doHttpRequest(nettyHttpRequest);
                        next = next.next;
                        if(next == this){
                            next = next.next;
                        }
                    }
                }
            });
        }
        //重连
        doConnect(true);
    }

    /**
     * 链接关闭后，把保存在队列中的请求全部返回错误，并清空队列
     */
    private  void requestErrorAfterConnectClosed(RpcException rpcException){
        LinkedList<NettyHttpRequest> waittingReSendList= new LinkedList<>();
        if(waittingReSendList.size()>0){
            waittingReSendQueue.drainTo(waittingReSendList);
            transferSentRequestExecutoer.submit(()->{
               waittingReSendList.forEach(x->x.getOnError().accept(rpcException,x));
            });
        }
    }

    /**
     * 在链接建立之后，把保存在队列中的请求全部发送出去，并清空队列
     */
    private void requestWriteAfterConnected(Channel channel){
        LinkedList<NettyHttpRequest> waittingReSendList= new LinkedList<>();
        if(waittingReSendList.size()>0){
            waittingReSendQueue.drainTo(waittingReSendList);
            transferSentRequestExecutoer.submit(()->{
                waittingReSendList.forEach(x->doWriteO(channel,x));
            });
        }
    }

    /**
     * 记录 最近活跃的时间
     */
    public void logLastActiveTime(){
        this.lastActiveTime = System.currentTimeMillis();
    }

    /**
     * 是否空闲
     */
    public boolean isIdle(){
        return System.currentTimeMillis() - lastActiveTime>maxIdleMillisecond;
    }

}
