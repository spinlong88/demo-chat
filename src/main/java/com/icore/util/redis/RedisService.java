package com.icore.util.redis;

import com.icore.util.LogPrintUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;

import java.util.Objects;

public class RedisService {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    private RedisFacade jedis;

    public RedisService(){

    }

    public RedisService(RedisFacade redisFacade){
        this.jedis = redisFacade;
    }

    public void setString(String key,String value,int seconds,String methodName){

        try{
            jedis.setString(key,value);
            if(seconds>0){
                jedis.expire(key,seconds);
            }
        }catch (Exception e){
            log.error(" redis setString erro e={}", LogPrintUtil.logExceptionTack(e));
        }finally {
            release(jedis,false);
        }
    }

    public String getString(String key,String methodName) {
        String value = "";
        try {
            if (jedis.exists(key)) {
                value = jedis.getString(key);
            }
        } catch (Exception e) {
            log.error(" redis setString erro e={}", LogPrintUtil.logExceptionTack(e));
        } finally {
            release(jedis, false);
        }
        return value;
    }

    public void release(RedisFacade jedis,boolean isBroken){
        try{
            if(Objects.nonNull(jedis)){
                jedis.close();
            }
        }catch(Exception e){
            log.error("RedisService release error ! e={}",e);
        }
    }

}
