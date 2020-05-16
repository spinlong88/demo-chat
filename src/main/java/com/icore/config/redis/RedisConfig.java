package com.icore.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Configuration
@Scope("singleton")
@ConfigurationProperties(prefix = "redis.pool")
@PropertySource(value = "redis.properties")
public class RedisConfig {
    private String host;
    private String port;
    private String password;
    private String maxActivte;
    private String maxIdle;
    private String minIdle;
    private String maxWait;
    private String testOnBorrow;
    private String testOnReturn;
    private String timeout;

    @Value("${host}")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Value("${port}")
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Value("${password}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Value("${maxActivte}")
    public String getMaxActivte() {
        return maxActivte;
    }

    public void setMaxActivte(String maxActivte) {
        this.maxActivte = maxActivte;
    }

    @Value("${maxIdle}")
    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    @Value("${minIdle}")
    public String getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    @Value("${maxWait}")
    public String getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    @Value("${testOnBorrow}")
    public String getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(String testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    @Value("${testOnReturn}")
    public String getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(String testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    @Value("${timeout}")
    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
