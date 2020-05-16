package com.icore;

import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import com.icore.util.SpringContexUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages  = {"com.icore" })
@EnableSwagger2
@MapperScan(value = "com.icore.repository")
public class ChatApplication {
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    public static void main(String[] args) {

        ConfigurableApplicationContext context =  SpringApplication.run(ChatApplication.class, args);
        //SpringContexUtil.setApplicationContext(context);
        log.info("start success......");
    }


}
