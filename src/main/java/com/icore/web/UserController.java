package com.icore.web;

import com.icore.model.UserModel;
import com.icore.service.UserService;
import com.icore.util.FastJsonUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
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
        log.info("UserController#getUserList userModelList={}", FastJsonUtil.toJSON(userModelList));
        return userModelList;
    }

    @RequestMapping(value="/getUser")
    public UserModel getUser(Long id){
       UserModel userModel = userService.getUser(id);
        log.info(" UserService  getUser  in userModel={}!", FastJsonUtil.toJSON(userModel));
        return userModel;
    }

    @RequestMapping(value="/deleteUser")
    public void deleteUser(UserModel userModel){
        userService.dealeteUser(new Long(32));
        log.info(" UserService  deleteUser  in userModel={}!", FastJsonUtil.toJSON(userModel));
    }

    @RequestMapping(value="/addUser")
    public void addUser(){
        UserModel userModel = new UserModel();
        userModel.setAge(32);
        userModel.setUsername("赖茂龙");
        userModel.setBirthday(new Date());
        userModel.setMobile("15815576007");
        userModel.setSalary(new BigDecimal("00"));
        userService.addUser(userModel);
        log.info("UserController#addUser user={},age={}", FastJsonUtil.toJSON(userModel),userModel.getAge());
    }

    @RequestMapping(value="/updateUser")
    public void updateUser(UserModel userModel){
        userService.updateUser(userModel);
        log.info("UserController#updateUser user={}", FastJsonUtil.toJSON(userModel));
    }


}
