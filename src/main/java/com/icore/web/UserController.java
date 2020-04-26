package com.icore.web;

import com.icore.model.UserModel;
import com.icore.service.UserService;
import com.icore.util.FastJsonUtils;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    @RequestMapping(value="/getUser")
    public void getUser(){
        userService.getUser();
    }

    @RequestMapping(value="/addUser")
    public void addUser(){
        UserModel userModel = new UserModel();
        userModel.setAge(32);
        userModel.setUsername("赖茂龙");
        userService.addUser(userModel);

        logger.trace("trace");
        logger.debug("debug");
        logger.warn("warn");
        log.info("UserController#addUser user={},age={}", FastJsonUtils.convertObjectToJSON(userModel),userModel.getAge());
        log.error("error");
    }


}
