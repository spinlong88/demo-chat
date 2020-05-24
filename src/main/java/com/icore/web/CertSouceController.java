package com.icore.web;

import com.icore.exception.BusinessException;
import com.icore.exception.ExceptionCode;
import com.icore.model.UserModel;
import com.icore.service.UserService;
import com.icore.util.*;
import com.icore.vo.common.PlatformResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;


@Api(value="/test", tags=APIInfo.User.USER_INFO)
@RestController
public class CertSouceController {

    @Autowired
    UserService userService;

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    @ApiOperation(value = APIInfo.User.ApiName.USER_GETLIST ,notes="查看用户列表")
    @RequestMapping(value="/encryptCert",method = RequestMethod.POST)
    public PlatformResponse<String> encryptCert(){
        try{
            UserModel userModel = new UserModel();
            userModel.setAge(22);
            userModel.setSalary(new BigDecimal(2222));
            userModel.setMobile("15815576007");
            userModel.setUsername("萨达啊22222");
            String parms = CertSourceHelper.encrypt(userModel,"");
            return PlatformResponse.success(parms);
        }catch(BusinessException e){
            log.error(" UserController#getUserList exception={} ",LogPrintUtil.logExceptionTack(e));
            return PlatformResponse.failure(e.getCode(),e.getMsg());
        }catch(Exception e){
            log.error(" UserController#getUserList exception={} ", LogPrintUtil.logExceptionTack(e));
            return PlatformResponse.failure(ExceptionCode.ERROR);
        }
    }


    @ApiOperation(value = APIInfo.User.ApiName.USER_UPDATE, notes="修改用户")
    @RequestMapping(value="/decryptCert",method = RequestMethod.POST)
    public PlatformResponse<UserModel> decryptCert(@RequestHeader("token") String params){
        try{
            UserModel userModel = CertSourceHelper.decrypt(params,"",UserModel.class);
            log.info("UserController#updateUser user={}", FastJsonUtil.toJSON(userModel));
            return PlatformResponse.success(userModel);
        }catch(BusinessException e){
            log.error(" UserController#getUserList exception={} ",LogPrintUtil.logExceptionTack(e));
            return PlatformResponse.failure(e.getCode(),e.getMsg());
        }catch(Exception e){
            log.error(" UserController#getUserList exception={} ", LogPrintUtil.logExceptionTack(e));
            return PlatformResponse.failure(ExceptionCode.ERROR);
        }
    }


}
