package com.icore.util.redis;

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
