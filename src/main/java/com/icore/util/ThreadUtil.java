package com.icore.util;

import com.google.common.collect.ImmutableMap;
import com.icore.filter.TraceIdHelper;
import org.slf4j.MDC;

import java.util.Map;

/**
 * 线程工具类
 */
public class ThreadUtil {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    private ThreadUtil(){

    }

    public static void warpRunnable(Runnable task, Map<String ,String> context){
        //获取父线程的traceId
        String pTraceId = context.get("traceId");
        MDC.setContextMap(ImmutableMap.of("traceId", TraceIdHelper.generateSubTraceId(pTraceId)));
        try{
            task.run();
        }catch (Exception e){
            log.error("ThreadUtil warpRunnable error task={},context={},error={}",task,context,LogPrintUtil.logExceptionTack(e));
            throw e;
        }finally {
            MDC.clear();
        }

    }


}
