package com.icore.demo.netty.httpClient.vo;

import io.netty.handler.codec.http.HttpMethod;
import lombok.Data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Data
public class NettyHttpRequest {

    /**
     * 默认发起请求头
     */
    private HttpMethod httpMethod = HttpMethod.POST;

    /**
     * 请求头
     */
    private Map<String, String> headerMap = new HashMap<>();

    /**
     * 请求体
     */
    private Object body;

    /**
     * 请求地址
     */
    private URL fullUrl;
    /**
     * 如果有返回值，那么做什么处理
     */
    private BiConsumer<String,NettyHttpRequest> onResponse;

    /**
     * 如果有异常，那么做什么处理
     */
    private BiConsumer<Throwable,NettyHttpRequest> onError;

    /**
     * traceId在日志中，将一次请求串起来
     */
    private String traceId;

    private int type = NORMAL;

    public static int NORMAL = 0;

    public static int PING = -1;

    public NettyHttpRequest(String fullUrl,Object body,Map<String,String> headerMap)throws MalformedURLException{
        this.fullUrl = new URL(fullUrl);
        this.body = body;
        this.headerMap = headerMap;
    }

    public String getPath(){
        if(httpMethod == HttpMethod.GET){
            return fullUrl.getPath()+"?"+fullUrl.getQuery();
        }
        return fullUrl.getPath();
    }


}
