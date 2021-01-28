package com.icore.demo.netty.httpClient.future;

import com.icore.demo.netty.httpClient.exception.RpcException;
import com.icore.demo.netty.httpClient.vo.NettyHttpRequest;
import com.icore.demo.netty.httpClient.vo.NettyHttpResponse;
import com.icore.util.FastJsonUtil;
import com.icore.util.LogPrintUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import sun.rmi.runtime.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ResponseFuture<T,R> {
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    private static final long DEFAULT_TIMEOUT_SECONDS = 5;

    private static final TimeUnit DEFAULT_TIMEOUT_UNIT = TimeUnit.SECONDS;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 响应
     */
    private NettyHttpResponse nettyHttpResponse = new NettyHttpResponse();

    /**
     * 请求
     */
    private T request;

    /**
     * 超时异常回调方法
     */
    private Function<T,R> fallbackMethod;

    /**
     * 自定义的返回值获取方法
     */
    private BiFunction<T,String,R> customGetRequestMethod;

    /**
     * 是否已经接收到相应(返回值或者异常，都算)
     */
    private boolean received = false;

    public ResponseFuture(T request){
        this.request = request;
    }

    public ResponseFuture(T request,Function<T,R> fallbackMethod){
        this.request = request;
        this.fallbackMethod = fallbackMethod;
    }

    public ResponseFuture(T request, Function<T,R> fallbackMethod, BiFunction<T,String,R> customGetRequestMethod){
        this.request = request;
        this.fallbackMethod = fallbackMethod;
        this.customGetRequestMethod = customGetRequestMethod;
    }

    /**
     * 如果请求出错的话，那么记录error
     * onError会在抛异常的时候出发(既onError.accept的方法被调用)
     */
    private BiConsumer<Throwable, NettyHttpRequest> onError = (error,request) ->{
        log.error(" netty client async error,request={},error={}",request, LogPrintUtil.logExceptionTack(error));
        this.nettyHttpResponse.setError(error);
        this.received = true;
        countDownLatch.countDown();
    };

    /**
     * 如果接收到相应的话，那么记录结果，且让当前线程不再阻塞
     * onResponse会在接收到返回结果的时候(既httpResponseHandler的channelRead)触发
     */
    private BiConsumer<String,NettyHttpRequest> onResponse = (result,request)->{
        this.nettyHttpResponse.setResult(result);
        this.received = true;
        countDownLatch.countDown();
    };

    public R get(Class<R> clazz){
        //如果已经接收到相应
        if(received){
            //异常检测
            exceptionCheck();

            //如果已经有返回值了，那么直接返回
            return getResult(clazz);

        }
        //如果没有接收到相应，那么阻塞等待
        try{
            countDownLatch.await();
        }catch (InterruptedException e){
            log.error(" e={}", LogPrintUtil.logExceptionTack(e));
            throw new RpcException(e);
        }

        //异常检测
        exceptionCheck();

        //如果已经有返回值了，那么直接返回
        return getResult(clazz);
    }

    public R getUnderDefaultTimeout(Class<R> clazz){
        return get(clazz,DEFAULT_TIMEOUT_SECONDS,DEFAULT_TIMEOUT_UNIT);
    }

    public R get(Class<R> clazz ,long timeout,TimeUnit timeUnit){

        //如果已经接收到相应
        if(received){
            //异常检测
            exceptionCheck();

            //如果已经有返回值了，那么直接返回
            return getResult(clazz);

        }
        //如果没有接收到相应，那么阻塞等待
        try{
            countDownLatch.await();
        }catch (InterruptedException e){
            log.error(" e={}", LogPrintUtil.logExceptionTack(e));
            throw new RpcException(e);
        }

        //如果还没有接收到相应，说明超时
        if(!received){
            log.error(" ResponseFuture get time timeout");
            if(fallbackMethod == null){
                throw new RpcException("timeout");
            }else {
                //如果存在超时处理方法，则调用
                return fallbackMethod.apply(request);
            }
        }
        //异常检测
        exceptionCheck();

        //如果已经有返回值了，那么直接返回
        return getResult(clazz);
    }

    /**
     * 异常检测
     */
    private void exceptionCheck(){
        if(nettyHttpResponse.getError()!=null){
            if(nettyHttpResponse.getError() instanceof RpcException){
                throw (RpcException)nettyHttpResponse.getError();
            }else {
                throw new RpcException(nettyHttpResponse.getError().getMessage(),nettyHttpResponse.getError());
            }
        }
    }

    /**
     * 获取返回结果
     * @param clazz
     * @return
     */
    public R getResult(Class<R> clazz){
        if(customGetRequestMethod != null){
            return customGetRequestMethod.apply(request,nettyHttpResponse.getResult());
        }
        return FastJsonUtil.toBean(nettyHttpResponse.getResult(),clazz);
    }

    public BiConsumer<Throwable,NettyHttpRequest> getOnError(){
        return onError;
    }

    public BiConsumer<String,NettyHttpRequest> getOnResponse(){
        return onResponse;
    }

    public NettyHttpResponse getNettyHttpResponse(){
        return nettyHttpResponse;
    }

    public void setReceived(boolean received){
        this.received = received;
    }



}
