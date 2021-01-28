package com.icore.demo.netty.httpClient.future;

import com.google.common.collect.ImmutableMap;
import com.icore.filter.TraceIdHelper;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.MDC;

public abstract class AbstractTraceFutureListener<V> implements GenericFutureListener<Future<? super V>> {
    private String parentTraceId;

    public void operationComplete(Future<? super V> future)throws Exception{
        MDC.setContextMap(ImmutableMap.of("traceId", TraceIdHelper.generateSubTraceId(parentTraceId)));
        try{
            doOperationComplete(future);
        }finally {
            MDC.clear();
        }
    }


    public abstract void doOperationComplete(Future<? super V> future)throws Exception;

}
