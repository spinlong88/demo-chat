package com.icore.exception;

import com.icore.util.CommonUtil;

public enum ExceptionCode {
  SUCCESS(0,"success"),
    Failure(-1,"fail");


  private Integer code;
  private String msg;

    ExceptionCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode(){return code;}

    public String getMsg(){return msg;}

    /**
     *消息格式化
     */
    public String formatMsg(Object... args){
        if(CommonUtil.isEmpty(this.getMsg()) || args.length == 0 ){
            return this.msg;
        }
        return String .format(this.msg,args);
    }
}
