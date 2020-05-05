package com.icore.vo.common;

import com.icore.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class PlatformResponse<T> {

    private String msg;
    private int code;
    private T data;

   //public static <T>PlatformResponse<T> success(T data){
       // return new PlatformResponse<>(ExceptionCode.SUCCESS.getCode(),ExceptionCode.SUCCESS.getMsg(),data);
   // }

    //public static <T>PlatformResponse<T> success(){
        // new PlatformResponse<>(ExceptionCode.SUCCESS.getCode(),ExceptionCode.SUCCESS.getMsg(),null);
   // }

    public PlatformResponse(String code,String msg,T data){}




}
