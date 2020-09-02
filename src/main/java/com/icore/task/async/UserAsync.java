package com.icore.task.async;

import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class UserAsync {


    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    @Async
    public void async(){
        try {
            log.info(" 异步打印：延迟开始+++++++++++++  ");
            Thread.sleep(5000);
            log.info(" 异步打印：延迟结束！！！！！！！！  ");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //带返回值的任务
    @Async("asyncServiceExecutor")
    public Future<String> doTask1() throws InterruptedException{
        log.info("异步打印：Task1 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        long end = System.currentTimeMillis();

        log.info("异步打印：Task1 finished, time elapsed: {} ms.", end-start);

        return new AsyncResult<>("Task1 accomplished!");
    }

    @Async("customServiceExecutor")
    public Future<String> doTask2() throws InterruptedException{
        log.info("异步打印：Task2 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();

        log.info("异步打印：ask2 finished, time elapsed: {} ms.", end-start);

        return new AsyncResult<>("Task2 accomplished!");
    }



}
