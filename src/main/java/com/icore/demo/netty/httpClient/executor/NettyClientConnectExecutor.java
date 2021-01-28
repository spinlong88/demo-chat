package com.icore.demo.netty.httpClient.executor;

import com.icore.config.executor.PolicyCallable;
import com.icore.util.LogPrintUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import com.icore.util.ThreadUtil;
import org.apache.tomcat.util.threads.TaskQueue;
import org.apache.tomcat.util.threads.TaskThreadFactory;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.*;

public class NettyClientConnectExecutor extends ThreadPoolExecutor {

    private static ThreadPoolExecutor connectExecutor = null;
    private static final MicroLogUtil log = MicroLogFactory.getLooger();


    public NettyClientConnectExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue){
        super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,new TaskThreadFactory("NettyClientCloseExecutor",false,5));
    }

    @Override
    public void execute(Runnable task){
        //将夫县城的traceId传递给子线程
        Map<String,String> map = MDC.getCopyOfContextMap();
        super.execute(()-> ThreadUtil.warpRunnable(task,map));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task){
        Map<String,String> map = MDC.getCopyOfContextMap();
        return super.submit(new PolicyCallable<T>(task,map));
    }

    @Override
    public  Future<?> submit(Runnable task){
        Map<String,String> map = MDC.getCopyOfContextMap();
        return super.submit(()-> ThreadUtil.warpRunnable(task,map));
    }

    public synchronized static ExecutorService getConnectExecutor(){
        if(null!=connectExecutor){
            return connectExecutor;
        }

        try{
            int closeInitThreadPool = (int)(Runtime.getRuntime().availableProcessors()*Double.parseDouble("2"));
            int closeMaxThreadPool = (int)(Runtime.getRuntime().availableProcessors()*Double.parseDouble("3"));
            int capactiy = (int)(Runtime.getRuntime().availableProcessors()*Double.parseDouble("1024"));
            TaskQueue taskQueue = new TaskQueue(capactiy);
            connectExecutor = new NettyClientCloseExecutor(closeInitThreadPool,closeMaxThreadPool,60,TimeUnit.MICROSECONDS,taskQueue);
            taskQueue.setParent(connectExecutor);
            log.error("NettyClientConnectExecutor getConnectExecutor closeInitThreadPool={},closeMaxThreadPool={}",closeInitThreadPool,closeMaxThreadPool);
        }catch (Exception e){
            log.error("NettyClientConnectExecutor getConnectExecutor error={}", LogPrintUtil.logExceptionTack(e));
        }
        return connectExecutor;

    }

}
