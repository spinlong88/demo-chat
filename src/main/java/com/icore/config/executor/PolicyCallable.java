package com.icore.config.executor;

import com.google.common.collect.ImmutableMap;
import com.icore.filter.TraceIdHelper;
import com.icore.util.LogPrintUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class PolicyCallable<T> implements Callable {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();
    private Callable<T> callable;
    private Map<String,String> context;

    @Override
    public Object call()throws Exception{
        String pTraceId = this.context.get("traceId");
        MDC.setContextMap(ImmutableMap.of("traceId", TraceIdHelper.generateSubTraceId(pTraceId)));
        try{
            return callable.call();
        }catch (Exception e){
            log.error("PolicyCallable call error={}", LogPrintUtil.logExceptionTack(e));
            throw e;
        }finally {
            MDC.clear();
        }
    }



}
