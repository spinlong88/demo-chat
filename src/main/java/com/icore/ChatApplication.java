package com.icore;

import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages  = {"com.icore.*" })
@EnableSwagger2
@MapperScan(value = "com.icore.repository")
public class ChatApplication {
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    public static void main(String[] args) {

        SpringApplication.run(ChatApplication.class, args);
        log.info("start success......");
    }


}
