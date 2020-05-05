package com.icore.util;

public class CommonUtil {

    public static boolean isEmpty(String value){
        if(value == null || "".equals(value)){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String value){
        return !isEmpty(value);
    }

}
