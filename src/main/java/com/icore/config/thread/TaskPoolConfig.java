package com.icore.config.thread;

import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class TaskPoolConfig {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();



    @Bean
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new VisiableThreadPoolTaskExecutor();
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(5);
        //配置队列大小
        threadPoolTaskExecutor.setQueueCapacity(50);
        //配置线程池前缀
        threadPoolTaskExecutor.setThreadNamePrefix("async-service-");
        //拒绝策略
//        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //threadPoolTaskExecutor.setRejectedExecutionHandler(new PrintingPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Bean
    public Executor customServiceExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor=new ThreadPoolTaskExecutor();
        //线程核心数目
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(10);
        //配置队列大小
        threadPoolTaskExecutor.setQueueCapacity(50);
        //配置线程池前缀
        threadPoolTaskExecutor.setThreadNamePrefix("custom-service-");
        //配置拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //数据初始化
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(10);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("hello-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        log.info("线程池创建成功！！！！！！！！！！！！！！！！！！！！！！！");
        return executor;
    }

}
