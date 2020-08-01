package com.icore.thread;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {
    private static ThreadContext CONTEXT = new ThreadContext();

    private static ThreadLocal<Map<String,Object>> CONTEXT_LOCAL = new ThreadLocal<Map<String,Object>>(){
        @Override
        protected Map<String,Object> initialValue(){
            return new HashMap<String,Object>();
        }
    };


    private ThreadContext(){

    }

    public static ThreadContext getContext(){
        return CONTEXT;
    }

    public boolean put(String key,Object value){
        CONTEXT_LOCAL.get().put(key,value);
        return true;
    }

    public Object get(String key){
        return CONTEXT_LOCAL.get().get(key);
    }

    public Object remove(String key){
        return CONTEXT_LOCAL.get().remove(key);
    }

    public void clear(){
        CONTEXT_LOCAL.get().clear();
    }


}
