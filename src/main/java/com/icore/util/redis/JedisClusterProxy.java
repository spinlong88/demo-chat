package com.icore.util.redis;

import lombok.AllArgsConstructor;
import redis.clients.jedis.JedisCluster;

@AllArgsConstructor
public class JedisClusterProxy extends RedisFacade{
    JedisCluster jedisCluster;

    @Override
    public void setString(String key ,String value) {
        jedisCluster.set(key,value);
    }

    @Override
    public String getString(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        return jedisCluster.expire(key,seconds);
    }

    @Override
    public boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    public void close(){
    }

}
