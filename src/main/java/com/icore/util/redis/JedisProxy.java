package com.icore.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisProxy extends RedisFacade{
    private JedisPool jedisPool;

    public JedisProxy(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }

    public Jedis getJedis(JedisPool jedisPool){
       return jedisPool.getResource();
    }

    @Override
    public void setString(String key,String value) {
        getJedis(jedisPool).set(key,value);
    }

    @Override
    public String getString(String key) {
        return getJedis(jedisPool).get(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        return getJedis(jedisPool).expire(key,seconds);
    }

    @Override
    public boolean exists(String key) {
        return getJedis(jedisPool).exists(key);
    }

    public void close(){
    }

}
