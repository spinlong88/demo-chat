package com.icore.util.redis;

import lombok.AllArgsConstructor;
import redis.clients.jedis.JedisCluster;

@AllArgsConstructor
public class JedisClusterProxy extends RedisFacade{
    JedisCluster jedisCluster;

    public void close(){
    }

}
