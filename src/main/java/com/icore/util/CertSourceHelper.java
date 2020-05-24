package com.icore.util;

import com.icore.exception.BusinessException;
import com.icore.exception.ExceptionCode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;

public class CertSourceHelper {

    public static String encrypt(Object request,String source){
        return encrypt(FastJsonUtil.toJSON(request),source);
    }

    public static String encrypt(String str,String source){
        String params = AESUtil.encrypt(str,"rf9z#tsklFg0iY*a").orElse(null);
        if(CommonUtil.isNotEmpty(params)){
            params = URLEncoder.encode(params);
        }
        return params;
    }

    public static <T> T decrypt(String encodeContent,String source,Class<T> t){
        return FastJsonUtil.toObject(decrypt(encodeContent,source),t);
    }

    public static String decrypt(String encodeContent,String source){
        String utf8Decoder = URLDecoder.decode(encodeContent);
        if(CommonUtil.isEmpty(utf8Decoder)){
            throw new BusinessException(ExceptionCode.ERROR);
        }

        Optional<String> jsonStr = AESUtil.decrypt(utf8Decoder,"rf9z#tsklFg0iY*a");
        if(!jsonStr.isPresent()){
            throw new BusinessException(ExceptionCode.ERROR);
        }
        return jsonStr.get();
    }

}
