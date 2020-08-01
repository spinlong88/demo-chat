package com.icore.filter;

import com.icore.thread.ThreadContext;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 日志追踪
 */
public class MicroInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion (HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse,
                                 Object obj,Exception exception)throws Exception{

        MDC.clear();
        ThreadContext.getContext().clear();
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse,
                             Object obj) throws Exception {
        httpServletResponse.setHeader("traceId",TraceIdHelper.initAndGetTraceId());
        return true;
    }



}
