package com.icore.filter;

import com.google.common.collect.ImmutableMap;
import com.icore.thread.ThreadContext;
import com.icore.util.CommonUtil;
import com.icore.util.UUIDUtil;
import org.slf4j.MDC;

import java.util.Optional;

public class TraceIdHelper {

    public static String initAndGetTraceId(){
        String traceId = generateTraceId();
        ThreadContext.getContext().put("traceId",traceId);
        MDC.setContextMap(ImmutableMap.of("traceId",traceId));
        return traceId;

    }

    public static String getTraceId(){
        Object o = ThreadContext.getContext().get("traceId");
        if(o!=null){
            return (String)o;
        }
        return null;
    }

    public static boolean existTraceId(){
        Optional<Object> objectOptional = Optional.ofNullable(ThreadContext.getContext().get("traceId"));
        return objectOptional.isPresent();
    }

    public static void clearTraceId(){
        ThreadContext.getContext().remove("traceId");
    }

    /**
     * 生成traceId
     * @return
     */
    public static String generateTraceId(){
        return "traceId="+ UUIDUtil.getUUID().toString().replace("-","");
    }

    /**
     * 生成子线程traceId
     * @param traceId
     * @return
     */
    public static String generateSubTraceId(String traceId){
        if(CommonUtil.isEmpty(traceId)){
            return generateTraceId();
        }
        return "traceId="+traceId;
    }


}
