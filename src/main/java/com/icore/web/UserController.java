package com.icore.web;

import com.icore.model.UserModel;
import com.icore.service.UserService;
import com.icore.util.FastJsonUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    @RequestMapping(value="/getUserList")
    public List<UserModel> getUserList(){
       List<UserModel> userModelList = userService.getUserList();
        log.info("UserController#getUserList userModelList={}", FastJsonUtil.convertObjectToJSON(userModelList));
        return userModelList;
    }

    @RequestMapping(value="/getUser")
    public UserModel getUser(){
       UserModel userModel = userService.getUser();
        log.info("UserController#getUserList userModel={}", FastJsonUtil.convertObjectToJSON(userModel));
        return userModel;
    }

    @RequestMapping(value="/deleteUser")
    public void deleteUser(){
        userService.dealeteUser(new Long(32));
    }

    @RequestMapping(value="/addUser")
    public void addUser(){
        UserModel userModel = new UserModel();
        userModel.setAge(32);
        userModel.setUsername("赖茂龙");
        userService.addUser(userModel);

        log.info("UserController#addUser user={},age={}", FastJsonUtil.convertObjectToJSON(userModel),userModel.getAge());
    }


}
