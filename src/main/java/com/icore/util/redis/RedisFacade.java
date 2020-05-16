package com.icore.util.redis;

public abstract class RedisFacade {

    public abstract void setString(String key,String value);

    public abstract String getString(String key);

    public abstract Long expire(String key ,int seconds);

    public abstract boolean exists(String key);

    public abstract void close();

}
