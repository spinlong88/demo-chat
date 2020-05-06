package com.icore.vo.common;

import com.icore.exception.ExceptionCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PlatformResponse<T> {

    private String msg;
    private int code;
    private T data;

    public static <T>PlatformResponse<T> success(T data){
        return new PlatformResponse<>(ExceptionCode.SUCCESS.getCode(),ExceptionCode.SUCCESS.getMsg(),data);
    }

    public static <T>PlatformResponse<T> success(){
         return new PlatformResponse<>(ExceptionCode.SUCCESS.getCode(),ExceptionCode.SUCCESS.getMsg(),null);
    }

    public static <T>PlatformResponse<T> failure(int code,String msg){
        return new PlatformResponse<>(code,msg);
    }

    public static <T>PlatformResponse<T> failure(ExceptionCode code,String msg){
        return new PlatformResponse<>(code.getCode(),msg);
    }

    public static <T>PlatformResponse<T> failure(ExceptionCode code,T data){
        return new PlatformResponse<>(code.getCode(),code.getMsg(),data);
    }

    public static <T>PlatformResponse<T> failure(int code,String msg,T data){
        return new PlatformResponse<>(code,msg,data);
    }

    public static <T>PlatformResponse<T> failure(ExceptionCode code){
        return new PlatformResponse<>(code.getCode(),code.getMsg());
    }

    public PlatformResponse(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public PlatformResponse(int code,String msg){
        this.code = code;
        this.msg = msg;
    }




}
