package com.icore.util.redis;

import com.google.common.base.Splitter;
import com.icore.config.redis.RedisConfig;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import com.icore.util.SpringContexUtil;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RedisPool {

    private static final MicroLogUtil log = MicroLogFactory.getLooger();
    private static RedisConfig redisConfig =  (RedisConfig) SpringContexUtil.getBean("redisConfg");
    public static  Map<String,RedisFacade> jedisMap = new ConcurrentHashMap<>();


    public static RedisFacade getInstance(String key){
        if(jedisMap.containsKey(key)){
            initJedis(key);
        }
        return jedisMap.get(key);

    }

    private static synchronized void initJedis(String key){
        if(jedisMap.containsKey(key)){
            return;
        }
        String host = redisConfig.getHost() == null?"127.0.0.1":redisConfig.getHost();
        String password = redisConfig.getPassword() == null?"a2833554":redisConfig.getPassword();
        int timeout = redisConfig.getTimeout() == null?2000:Integer.valueOf(redisConfig.getTimeout());
        String port = redisConfig.getPort() == null?"6379":redisConfig.getPort();
        int maxActivte = redisConfig.getMaxActivte() ==null?300:Integer.valueOf(redisConfig.getMaxActivte());
        int maxWait = redisConfig.getMaxWait() == null?300:Integer.valueOf(redisConfig.getMaxWait());
        int maxIdle = redisConfig.getMaxIdle() == null?0:Integer.valueOf(redisConfig.getMaxIdle());
        int minIdle = redisConfig.getMinIdle() == null?0:Integer.valueOf(redisConfig.getMinIdle());
        boolean testOnBorrow = redisConfig.getTestOnBorrow() == null?false:Boolean.valueOf(redisConfig.getTestOnBorrow());
        boolean testOnReturn = redisConfig.getTestOnReturn() == null?false:Boolean.valueOf(redisConfig.getTestOnReturn());

        log.info(" redis host={},port={},password={}",host,port,password);

        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        List<String> ports = Splitter.on(",").splitToList(port);
        List<String> hosts = Splitter.on(",").splitToList(host);

        for(int i=0;i< hosts.size();i++){
            nodes.add(new HostAndPort(hosts.get(i),Integer.valueOf(ports.get(i))));
        }

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActivte);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        if(hosts.size() !=1){
            JedisCluster cluster = new JedisCluster(nodes,timeout,timeout,maxActivte,password,config);
            jedisMap.put(key,new JedisClusterProxy(cluster));
        }else{
            JedisPool jedisPool = new JedisPool(config,host,Integer.valueOf(port),timeout,true);
            jedisMap.put(key, new JedisProxy(jedisPool));
        }
        log.info(" init jedisPool successful ");


    }

}
