package com.icore;

import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.icore.repository")
public class ChatApplication {
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    public static void main(String[] args) {

        SpringApplication.run(ChatApplication.class, args);
        log.info("start success......");
    }

}
