package com.icore.sensitive.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.icore.util.CommonUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

public class SensitiveInfoUtils {


    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     *
     * @param mobile
     * @return
     */
    public static String mobilePhone(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        mobile= mobile.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        return mobile;
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param fullName
     * @return
     */
    public static String chineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }


    /**
     * 获取脱敏json串 <注意：递归引用会导致java.lang.StackOverflowError>
     *
     * @param obj
     * @return
     */
    public static String getSensitiveLog(Object obj) {
        System.out.println(obj+"-------------------1");
        StringBuilder sb = new StringBuilder("");
        String sensitivejson = "";
        if (obj instanceof Collection<?>){
            List javaBeanList = (List)obj;

            for(int i=0;i<javaBeanList.size();i++){
                obj = javaBeanList.get(i);
                sb.append(getLog(obj));
            }
            return sb.toString();
        }
        sensitivejson = getLog(obj);
        return sensitivejson;
    }

    private static String getLog(Object javaBean){
        String json = null;
        if (null != javaBean) {
            Class<? extends Object> raw = javaBean.getClass();
            try {
                if (raw.isInterface()){
                    return null;
                }

                Gson g = new Gson();
                Object clone = g.fromJson(g.toJson(javaBean, javaBean.getClass()), javaBean.getClass());
                System.out.println(clone+"-------------------2");
                Set<Integer> referenceCounter = new HashSet<Integer>();
                System.out.println(clone+"-------------------3");
                SensitiveInfoUtils.replace(SensitiveInfoUtils.findAllField(raw), clone, referenceCounter);
                System.out.println(clone+"-------------------4");
                json = JSON.toJSONString(clone, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);
                referenceCounter.clear();
                referenceCounter = null;
            } catch (Exception ex) {
//                logger.error("SensitiveInfoUtils.getJson() ERROR", e);
            }
        }

        return json;
    }

    private static Field[] findAllField(Class<?> clazz) {
        Field[] fileds = clazz.getDeclaredFields();
        while (null != clazz.getSuperclass() && !Object.class.equals(clazz.getSuperclass())) {
            fileds = (Field[]) ArrayUtils.addAll(fileds, clazz.getSuperclass().getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fileds;
    }
    private static void replace(Field[] fields, Object javaBean, Set<Integer> referenceCounter) throws IllegalArgumentException, IllegalAccessException {
        if (null != fields && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                if (null != field && null != javaBean) {
                    Object value = field.get(javaBean);
                    if (null != value) {
//                        System.out.println(value);
                        Class<?> type = value.getClass();
//                        System.out.println(type);
                        // 1.处理子属性，包括集合中的
                        if (type.isArray()) {
                            int len = Array.getLength(value);
                            for (int i = 0; i < len; i++) {
                                Object arrayObject = Array.get(value, i);
                                SensitiveInfoUtils.replace(SensitiveInfoUtils.findAllField(arrayObject.getClass()), arrayObject, referenceCounter);
                            }
                        } else if (value instanceof Collection<?>) {
                            Collection<?> c = (Collection<?>) value;
                            Iterator<?> it = c.iterator();
                            while (it.hasNext()) {
                                Object collectionObj = it.next();
                                SensitiveInfoUtils.replace(SensitiveInfoUtils.findAllField(collectionObj.getClass()), collectionObj, referenceCounter);
                            }
                        } else if (value instanceof Map<?, ?>) {
                            Map<?, ?> m = (Map<?, ?>) value;
                            Set<?> set = m.entrySet();
                            for (Object o : set) {
                                Entry<?, ?> entry = (Entry<?, ?>) o;
                                Object mapVal = entry.getValue();
                                SensitiveInfoUtils.replace(SensitiveInfoUtils.findAllField(mapVal.getClass()), mapVal, referenceCounter);
                            }
                        }
                        else if (!type.isPrimitive()
                                && !StringUtils.startsWith(type.getPackage().getName(), "javax.")
                                && !StringUtils.startsWith(type.getPackage().getName(), "java.")
                                && !StringUtils.startsWith(field.getType().getName(), "javax.")
                                && !StringUtils.startsWith(field.getName(), "java.")
                                && referenceCounter.add(value.hashCode())) {
                            SensitiveInfoUtils.replace(SensitiveInfoUtils.findAllField(type), value, referenceCounter);
                        }
                    }
                    // 2. 处理自身的属性
                    SensitiveInfo annotation = field.getAnnotation(SensitiveInfo.class);
                    if (field.getType().equals(String.class) && null != annotation) {
                        String valueStr = (String) value;
                        if (CommonUtil.isNotEmpty(valueStr)) {
                            switch (annotation.type()) {
                                case CHINESE_NAME: {
                                    field.set(javaBean, SensitiveInfoUtils.chineseName(valueStr));
                                    break;
                                }
                                case MOBILE_PHONE: {
                                    field.set(javaBean, SensitiveInfoUtils.mobilePhone(valueStr));
                                    break;
                                }

                            }
                        }
                    }
                }
            }
        }
    }











}
