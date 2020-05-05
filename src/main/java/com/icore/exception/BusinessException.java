package com.icore.exception;

import com.icore.util.CommonUtil;

public class BusinessException extends RuntimeException {

    private ExceptionCode code;
    private String msg;
    private Throwable cause;

    public BusinessException(ExceptionCode coed){this.code = code;}
    public BusinessException(ExceptionCode coed,Throwable cause){
        this.code= code;
        this.cause=cause;
    }

    public ExceptionCode getCode(){return code;}

    public String getMsg(){return CommonUtil.isEmpty(msg)?code.getMsg():this.msg;}

    public Throwable getCause(){return cause;}
}
