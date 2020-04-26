package com.icore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MicroLogUtil {
    private Logger log;

    private static class SingletonHolder{
        private static MicroLogUtil microLogUtil = new MicroLogUtil();
    }

    public static MicroLogUtil getInstance(){
        return SingletonHolder.microLogUtil;
    }

    private MicroLogUtil(){
        log = LoggerFactory.getLogger(MicroLogUtil.class);
    }

    public void info(String info){
        String infoFormat = generatorPrixMsg()+info;
        log.info(infoFormat);
    }

    public void info(String info,Object object){
        String infoFormat = generatorPrixMsg()+info;
        log.info(infoFormat,object);
    }
    public void info(String info,Object... object){
        String infoFormat = generatorPrixMsg()+info;
        log.info(infoFormat,object);
    }

    public void info(String info,Throwable throwable){
        String infoFormat = generatorPrixMsg()+info;
        log.info(infoFormat,throwable);
    }

    public void error(String info){
        String infoFormat = generatorPrixMsg()+info;
        log.error(infoFormat);
    }

    public void error(String info,Object object){
        String infoFormat = generatorPrixMsg()+info;
        log.error(infoFormat,object);
    }
    public void error(String info,Object... object){
        String infoFormat = generatorPrixMsg()+info;
        log.error(infoFormat,object);
    }

    public void error(String info,Throwable throwable){
        String infoFormat = generatorPrixMsg()+info;
        log.error(infoFormat,throwable);
    }

    private static String generatorPrixMsg(){
        final StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        String msg ="";
        if((stackTraceElement.length>3) && (stackTraceElement[3]!=null) ){
            final StackTraceElement stack = stackTraceElement[3];
            final String clazzName = stack.getClassName();
            msg = clazzName+"."+stack.getMethodName()+"("+clazzName.substring(clazzName.lastIndexOf(".")+1,clazzName.length())
                    +".java:"+stack.getLineNumber()+") - ";
        }
        return msg;
    }



}
