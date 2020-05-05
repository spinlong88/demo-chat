package com.icore.web;

import com.icore.model.UserModel;
import com.icore.service.UserService;
import com.icore.util.FastJsonUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Api(value="/test", tags="测试接口模块")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    @ApiOperation(value = "增加用户", notes="增加用户")
    @RequestMapping(value="/addUser",method = RequestMethod.POST)
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

    @ApiOperation(value = "查看用户列表", notes="查看用户列表")
    @RequestMapping(value="/getUserList",method = RequestMethod.POST)
    public List<UserModel> getUserList(){
       List<UserModel> userModelList = userService.getUserList();
        log.info("UserController#getUserList userModelList={}", FastJsonUtil.toJSON(userModelList));
        return userModelList;
    }


    @ApiOperation(value = "查看用户", notes="查看用户")
    @RequestMapping(value="/getUser",method = RequestMethod.POST)
    public UserModel getUser(UserModel userModel){
       UserModel queryUserModel = userService.getUser(userModel.getId());
        log.info(" UserService  getUser  in queryUserModel={}!", FastJsonUtil.toJSON(queryUserModel));
        return userModel;
    }

    @ApiOperation(value = "删除用户", notes="删除用户")
    @RequestMapping(value="/deleteUser",method = RequestMethod.POST)
    public void deleteUser(UserModel userModel){
        userService.dealeteUser(userModel.getId());
        log.info(" UserService  deleteUser  in userModel={}!", FastJsonUtil.toJSON(userModel));
    }

    @ApiOperation(value = "修改用户", notes="修改用户")
    @RequestMapping(value="/updateUser",method = RequestMethod.POST)
    public void updateUser(UserModel userModel){
        userService.updateUser(userModel);
        log.info("UserController#updateUser user={}", FastJsonUtil.toJSON(userModel));
    }


}
