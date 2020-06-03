package com.icore.util.redis;

import com.icore.model.UserModel;
import com.icore.util.FastJsonUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import com.icore.util.comConst.RedisConst;

import java.rmi.server.ExportException;
import java.util.Objects;
import java.util.Optional;

public class TokenHelper {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    public static void tokenToRedis(String token, UserModel userModel){
        RedisServiceFactory.getRedisService(RedisConst.REDIS_CENTER).setString(key(token), FastJsonUtil.toJSON(userModel),864000,"");
    }

    public static String key(String token){
        return RedisConst.REDIS_CENTER + token;
    }

    public static Optional<UserModel> ofTokenOnly(String token){
        try{
            String str = RedisServiceFactory.getRedisService(RedisConst.REDIS_CENTER).getString(key(token));
            UserModel userModel = FastJsonUtil.toBean(str,UserModel.class);
            return Optional.ofNullable(userModel);
        }catch (Exception e){
            log.error(" TokenHelper ofTokenOnly error={}",e);
        }
        return Optional.empty();
    }

    public static Optional<UserModel> ofToken(String token){
        try{
            UserModel userModel = ofTokenOnly(token).orElse(null);
            if(Objects.nonNull(userModel)){
                tokenToRedis(token,userModel);
            }
            return Optional.ofNullable(userModel);
        }catch (Exception e){
            log.error(" TokenHelper ofToken error={}",e);
        }
        return Optional.empty();

    }




}
