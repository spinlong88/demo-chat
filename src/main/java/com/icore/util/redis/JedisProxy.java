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

    public void close(){
    }

}
