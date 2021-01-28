package com.icore.demo.netty.httpClient.client;

import com.icore.demo.netty.httpClient.config.NettyClientConfig;
import com.icore.demo.netty.httpClient.conn.MultiNettyHttpConnection;
import com.icore.demo.netty.httpClient.future.ResponseFuture;
import com.icore.demo.netty.httpClient.vo.NettyHttpRequest;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.MDC;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import static com.icore.demo.netty.httpClient.vo.NettyHttpRequest.NORMAL;

/**
 * 长链接的netty客户端
 */
public class LongConnectionNettyClient {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    private MultiNettyHttpConnection multiNettyHttpConnection;

    public LongConnectionNettyClient(NettyClientConfig nettyClientConfig){
        if(nettyClientConfig == null){
            throw new NullPointerException("config");
        }
        multiNettyHttpConnection = new MultiNettyHttpConnection(nettyClientConfig);
        multiNettyHttpConnection.connect();
    }

    public LongConnectionNettyClient(String url){
        this(NettyClientConfig.createByUrl(url));
    }

    public <T,R> R post(String url,T body,Class<R> clazz){
        return post(url,body,null,clazz);
    }

    public <T,R> R post(String url, T body, Map<String,String> headerMap, Class<R> clazz){
       //异步调用
        ResponseFuture<T,R> responseFuture = postAsync(url,body,headerMap);
        //阻塞获取结果
        return responseFuture.get(clazz);
    }

    public <T,R> R post(String url, T body,Class<R> clazz, long timeout, TimeUnit timeUnit){
        return post(url,body,null,clazz,timeout,timeUnit);
    }

    public <T,R> R post(String url, T body, Class<R> clazz, long timeout, TimeUnit timeUnit, Function<T,R> fallbackMethod){
        return post(url,body,null,clazz,timeout,timeUnit,fallbackMethod);
    }

    public <T,R> R post(String url, T body,Map<String,String> headerMap, Class<R> clazz, long timeout, TimeUnit timeUnit){
        return post(url,body,headerMap,clazz,timeout,timeUnit,null);
    }

    public <T,R> R post(String url, T body, Map<String,String> headerMap, Class<R> clazz, long timeout, TimeUnit timeUnit, Function<T,R> fallbackMethod){
        //异步调用
        ResponseFuture<T,R> responseFuture = postAsync(url,body,headerMap,fallbackMethod);
        //阻塞获取结果
        return responseFuture.get(clazz,timeout,timeUnit);
    }

    public <T,R> R post(String url, T body, Map<String,String> headerMap, Class<R> clazz, long timeout, TimeUnit timeUnit, Function<T,R> fallbackMethod,
                        BiFunction<T,String,R> customGetResultMethod){
        //异步调用
        ResponseFuture<T,R> responseFuture = postAsync(url,body,headerMap,fallbackMethod,customGetResultMethod);
        //阻塞获取结果
        return responseFuture.get(clazz,timeout,timeUnit);
    }

    public <T,R> ResponseFuture<T,R> postAsync(String url,T body){
        return postAsync(url,body,null);
    }

    public <T,R> ResponseFuture<T,R> postAsync(String url,T body, Map<String,String> headerMap){
        return postAsync(url,body,headerMap,null);
    }

    public <T,R> ResponseFuture<T,R> postAsync(String url,T body, Map<String,String> headerMap,Function<T,R> fallbackMethod){
        ResponseFuture<T,R> responseFuture = new ResponseFuture<>(body,fallbackMethod);
        asyncDoHttpRequest(url,body,headerMap, HttpMethod.POST,responseFuture.getOnResponse(),responseFuture.getOnError());
        return responseFuture;
    }

    public <T,R> ResponseFuture<T,R> postAsync(String url,T body, Map<String,String> headerMap,Function<T,R> fallbackMethod,
                                               BiFunction<T,String,R> customGetResultMethod){
        ResponseFuture<T,R> responseFuture = new ResponseFuture<>(body,fallbackMethod,customGetResultMethod);
        asyncDoHttpRequest(url,body,headerMap, HttpMethod.POST,responseFuture.getOnResponse(),responseFuture.getOnError());
        return responseFuture;
    }

    public <R> R get(String url,Class<R> clazz){
        return get(url,null,clazz);
    }

    public <R> R get(String url,Map<String,String> headerMap,Class<R> clazz){
        ResponseFuture<Object,R> responseFuture = getAsync(url,headerMap);
        return responseFuture.get(clazz);
    }

    public <R> R get(String url,Class<R> clazz,long timeout,TimeUnit timeUnit){
        return get(url,null,clazz,timeout,timeUnit);
    }

    public <R> R get(String url,Class<R> clazz,long timeout,TimeUnit timeUnit,Function<Object,R> fallbackMethod){
        return get(url,null,clazz,timeout,timeUnit,fallbackMethod);
    }

    public <R> R get(String url,Map<String,String> headerMap,Class<R> clazz,long timeout,TimeUnit timeUnit){
        return get(url,headerMap,clazz,timeout,timeUnit,null);
    }

    public <R> R get(String url,Map<String,String> headerMap,Class<R> clazz,long timeout,TimeUnit timeUnit,Function<Object,R> fallbackMethod){
        ResponseFuture<Object,R> responseFuture = getAsync(url,headerMap,fallbackMethod);
        return responseFuture.get(clazz,timeout,timeUnit);
    }

    public <R> ResponseFuture<Object ,R>getAsync(String url){
        return getAsync(url,null);
    }

    public <R> ResponseFuture<Object ,R>getAsync(String url,int requestType){
        return getAsync(url,null,requestType,null);
    }

    public <R> ResponseFuture<Object ,R>getAsync(String url,Map<String,String> headerMap){
        return getAsync(url,headerMap,null);
    }

    public <R> ResponseFuture<Object ,R>getAsync(String url,Map<String,String> headerMap,Function<Object,R> fallbackMethod){
        return getAsync(url,headerMap,NORMAL,fallbackMethod);
    }

    public <R> ResponseFuture<Object,R> getAsync(String url, Map<String,String> headerMap,int requestType,Function<Object,R> fallbackMethod){
        ResponseFuture<Object,R> responseFuture = new ResponseFuture<>(null,fallbackMethod);
        asyncDoHttpRequest(url,null,headerMap, HttpMethod.GET,requestType,responseFuture.getOnResponse(),responseFuture.getOnError());
        return responseFuture;
    }

    private void asyncDoHttpRequest(String url, Object body, Map<String,String> headerMap, HttpMethod httpMethod,
                                    BiConsumer<String, NettyHttpRequest> onResponse, BiConsumer<Throwable, NettyHttpRequest> onError){
        asyncDoHttpRequest(url,body,headerMap,httpMethod,NORMAL,onResponse,onError);

    }

    private void asyncDoHttpRequest(String url, Object body, Map<String,String> headerMap, HttpMethod httpMethod, int requestType,
                                    BiConsumer<String, NettyHttpRequest> onResponse, BiConsumer<Throwable, NettyHttpRequest> onError){

        NettyHttpRequest nettyHttpRequest;
        try{
            nettyHttpRequest = new NettyHttpRequest(url,body,headerMap);

        }catch (MalformedURLException e){
            throw new RuntimeException("URL不规范");
        }
        nettyHttpRequest.setOnError(onError);
        nettyHttpRequest.setOnResponse(onResponse);
        String traceId = MDC.getCopyOfContextMap().get("traceId");
        nettyHttpRequest.setTraceId(traceId);

        nettyHttpRequest.setHttpMethod(httpMethod);
        nettyHttpRequest.setType(requestType);

        //发起http请求
        multiNettyHttpConnection.doHttpRequest(nettyHttpRequest);

    }

    public void close(){
        multiNettyHttpConnection.close();
    }

    public boolean isIdle(){
        return multiNettyHttpConnection.isAllIdle();
    }


}
