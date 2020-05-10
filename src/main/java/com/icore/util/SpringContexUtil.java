package com.icore.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContexUtil implements ApplicationContextAware {

    private static ApplicationContext applicantionContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContexUtil.applicantionContext = applicantionContext;
    }

    public static ApplicationContext getApplicantionContext(){
        return applicantionContext;
    }

    public static Object getBean(String name){
        return applicantionContext.getBean(name);
    }

    public static Object getBean(String name ,Class requiredType){
        return applicantionContext.getBean(name ,requiredType);
    }

    public static boolean containBean(String name){
        return applicantionContext.containsBean(name);
    }

    public static boolean isSingleton(String name){
        return applicantionContext.isSingleton(name);
    }

    public static Class getType(String name){
        return applicantionContext.getType(name);
    }

    public static String[] getAliases(String name){
        return applicantionContext.getAliases(name);
    }



}
