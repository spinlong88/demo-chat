package com.icore.web;

import com.google.common.collect.ImmutableMap;
import com.icore.aop.LogAnnotation;
import com.icore.demo.netty.httpClient.client.LongConnectionNettyClient;
import com.icore.demo.netty.httpClient.factory.LongConnectionNettyClientFactory;
import com.icore.exception.BusinessException;
import com.icore.exception.ExceptionCode;
import com.icore.model.UserModel;
import com.icore.service.UserService;
import com.icore.task.async.UserAsync;
import com.icore.util.FastJsonUtil;
import com.icore.util.LogPrintUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import com.icore.util.comConst.RedisConst;
import com.icore.util.redis.RedisServiceFactory;
import com.icore.vo.common.PlatformResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;


@Api(value="/test", tags=APIInfo.User.USER_INFO)
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserAsync userAsync;

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    @ApiOperation(value = APIInfo.User.ApiName.USER_GETLIST ,notes="查看用户列表")
    @RequestMapping(value="/getUserList",method = RequestMethod.POST)
    public PlatformResponse<List<UserModel>> getUserList(){
        try{
            RedisServiceFactory.getRedisService(RedisConst.REDIS_CENTER).setString("fcuk","asd",86400,"getUserList");
            String redisValue = RedisServiceFactory.getRedisService(RedisConst.REDIS_CENTER).getString("fcuk");
            //userAsync.async();
            System.out.println(redisValue);
            //事务
            //userService.testTrans();
            List<UserModel> userModelList = userService.getUserList();
            log.info("UserController#getUserList userModelList={}", FastJsonUtil.toJSON(userModelList));
            return PlatformResponse.success(userModelList);
        }catch(BusinessException e){
            log.error(" UserController#getUserList exception={} ",LogPrintUtil.logExceptionTack(e));
            return PlatformResponse.failure(e.getCode(),e.getMsg());
        }catch(Exception e){
            log.error(" UserController#getUserList exception={} ", LogPrintUtil.logExceptionTack(e));
            return PlatformResponse.failure(ExceptionCode.ERROR);
        }

    }


    @ApiOperation(value = APIInfo.User.ApiName.USER_GET, notes="查看用户")
    @RequestMapping(value="/getUser",method = RequestMethod.POST)
    @LogAnnotation
    public PlatformResponse<UserModel> getUser(@RequestBody UserModel userModel){
        try{
        Future<String> sad = userAsync.doTask1();
        log.info(" 接口打印：UserService  getUser  in userModel={}!", FastJsonUtil.toJSON(userModel));
        UserModel queryUserModel = userService.getUser(userModel.getId());
        log.info(" 接口打印：UserService  getUser  out userModel={}!", FastJsonUtil.toJSON(queryUserModel));
        BeanUtils.copyProperties(queryUserModel,userModel);
        return PlatformResponse.success(queryUserModel);
        }catch(BusinessException e){
            log.error(" UserController#getUser exception={} ",LogPrintUtil.logExceptionTack(e));
            return PlatformResponse.failure(e.getCode(),e.getMsg());
        }catch(Exception e){
            log.error(" UserController#getUser exception={} ", LogPrintUtil.logExceptionTack(e));
            return PlatformResponse.failure(ExceptionCode.ERROR);
        }
    }

    @ApiOperation(value = APIInfo.User.ApiName.USER_ADD, notes="增加用户")
    @RequestMapping(value="/addUser",method = RequestMethod.POST)
    public PlatformResponse addUser(){
        UserModel userModel = new UserModel();
        userModel.setAge(32);
        userModel.setUsername("赖茂龙");
        userModel.setBirthday(new Date());
        userModel.setMobile("15815576007");
        userModel.setSalary(new BigDecimal("18000"));
        userService.addUser(userModel);
        log.info("UserController#addUser user={},age={}", FastJsonUtil.toJSON(userModel),userModel.getAge());
        return PlatformResponse.success();
    }

    @ApiOperation(value = APIInfo.User.ApiName.USER_DELETE, notes="删除用户")
    @RequestMapping(value="/delUser",method = RequestMethod.POST)
    public PlatformResponse deleteUser(@RequestBody UserModel userModel){
        userService.delUser(userModel.getId());
        log.info(" UserService  delUser  in userModel={}!", FastJsonUtil.toJSON(userModel));
        return PlatformResponse.success();
    }

    @ApiOperation(value = APIInfo.User.ApiName.USER_UPDATE, notes="修改用户")
    @RequestMapping(value="/updateUser",method = RequestMethod.POST)
    public PlatformResponse updateUser(@RequestBody UserModel userModel){
        userService.updateUser(userModel);
        log.info("UserController#updateUser user={}", FastJsonUtil.toJSON(userModel));
        return PlatformResponse.success();
    }

    @ApiOperation(value = APIInfo.User.ApiName.USER_UPDATE, notes="修改用户")
    @RequestMapping(value="/testNetty",method = RequestMethod.GET)
    public PlatformResponse testNetty(String host){
        String url = "http://192.168.3.31:8080/center/getUserList";
        LongConnectionNettyClient connectionNettyClient = LongConnectionNettyClientFactory.getOrCreateLongConnectionNettyClient(url);
        HashMap<String,String> head = new HashMap<>();
        head.put("content-type","application/json");

        ImmutableMap.Builder<String,Object> body = ImmutableMap.builder();
        body.put("","");

        PlatformResponse userList = connectionNettyClient.post(url,body,head,PlatformResponse.class);
        log.info("UserController#testNetty userList={}", FastJsonUtil.toJSON(userList));
        return PlatformResponse.success(userList);
    }

    @ApiOperation(value = APIInfo.User.ApiName.USER_UPDATE, notes="修改用户")
    @RequestMapping(value="/testNetty2",method = RequestMethod.GET)
    public PlatformResponse testNetty2(String host){
        String url = "http://192.168.3.31:8080/center/getUser";
        LongConnectionNettyClient connectionNettyClient = LongConnectionNettyClientFactory.getOrCreateLongConnectionNettyClient(url);
        HashMap<String,String> head = new HashMap<>();
        head.put("content-type","application/json");

        ImmutableMap.Builder<String,Object> builder = ImmutableMap.builder();
        ImmutableMap<String,Object> body = builder.put("id",new Long(6)).build();
        PlatformResponse user = connectionNettyClient.post(url,body,head,PlatformResponse.class);
        log.info("UserController#testNetty2 user={}", FastJsonUtil.toJSON(user));
        return PlatformResponse.success(user);
    }





}
