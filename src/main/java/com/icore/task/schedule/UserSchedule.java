package com.icore.task.schedule;

import com.icore.model.UserModel;
import com.icore.service.UserService;
import com.icore.util.FastJsonUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSchedule {

    @Autowired
    UserService userService;
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    //@Scheduled(cron="* 10 *  * * ? ")
    public void execute(){
        List<UserModel> userModelList = userService.getUserList();
        log.info("UserSchedule#execute userModelList={}", FastJsonUtil.toJSON(userModelList));

    }

}
