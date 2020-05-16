package com.icore.util.redis;

public class RedisServiceFactory {

    private RedisServiceFactory(){

    }

    public static RedisService getRedisService(String key){
        return new RedisService(RedisPool.getInstance(key));
    }

}
