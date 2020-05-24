package com.icore.util;

import java.util.Base64;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class AESUtil{

    private static final String IV_STRING = "2281698499864041";//默认的加密算法

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param key 加密密钥
     * @return 返回Base64转码后的加密数据
     */
    public static Optional<String> encrypt(String content, String key) {
        try {
           byte[] byteContent = content.getBytes("UTF-8");
           byte[] enCodeFormat = key.getBytes("UTF-8");
           SecretKeySpec SecretKeySpec = new SecretKeySpec(enCodeFormat,"AES");
           byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,SecretKeySpec,ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(byteContent);
            Base64.Encoder encoder = Base64.getEncoder();
            return Optional.ofNullable(encoder.encodeToString(encryptedBytes));
        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param key
     * @return
     */
    public static Optional<String> decrypt(String content, String key) {

        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedBytes = decoder.decode(content);
            byte[] enCodeFormat = key.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat,"AES");
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,secretKey,ivParameterSpec);
            byte[] result = cipher.doFinal(encryptedBytes);
            return Optional.ofNullable(new String(result,"UTF-8"));

        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Optional.empty();
    }

    public static String getRandStr(int num){
        String strs = "Aq12BCDE!w3aFGzH7eIsZKLrMx0NO4^tdPQcRSy6fT%UvVW5ubg$hSYn8ijmZ#9o@plS321M982L?_+3";
        StringBuffer buff = new StringBuffer();
        for(int i=1;i<=num;i++){
            char str = strs.charAt((int)(Math.random()*80));
            if(buff.toString().contains(String.valueOf(str))){
                i = i-1;
                continue;
            }
            buff.append(str);
        }
        return buff.toString();
    }


    public static void main(String[] args) {
       String str = getRandStr(16);
       System.out.println(str);

    }
}