package com.icore.task.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserAsync {

    @Async
    public void async(){
        try {
            System.out.println(" 延迟开始  ") ;
            Thread.sleep(5000);
            System.out.println(" 延迟结束  ") ;
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
