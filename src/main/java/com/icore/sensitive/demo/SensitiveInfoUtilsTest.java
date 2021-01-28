package com.icore.sensitive.demo;

import com.icore.model.UserModel;

import java.util.*;

public class SensitiveInfoUtilsTest {

    public static void main(String[] args) {
        JavaBeanA a1 = new JavaBeanA("","");
        JavaBeanA a2 = new JavaBeanA("","");
        JavaBeanB b1 = new JavaBeanB();
        a1.setB(b1);
//        a1.setDate(new Date());

        List<JavaBeanB> a1l = new ArrayList<JavaBeanB>();
        a1l.add(b1);
        a1.setList(a1l);
        Map<String, JavaBeanB> a1m = new HashMap<String, JavaBeanB>();
        a1m.put("b1", b1);
        a1.setMap(a1m);

        b1.setA(a2);
        Set<JavaBeanA> b1l = new HashSet<JavaBeanA>();
        b1.setList(b1l);
        Map<String, JavaBeanA> b1m = new HashMap<String, JavaBeanA>();
        b1m.put("a2", a2);
        b1.setMap(b1m);
//        long t = System.currentTimeMillis();
//        System.out.println(t);
//        System.out.println(SensitiveInfoUtils.getJson(a1));
//        System.out.println(System.currentTimeMillis()-t);
//        System.out.println(FastJsonUtil.toJSON(a1));
//        System.out.println(System.currentTimeMillis()-t);
        UserModel userModel2= new UserModel();
        userModel2.setUsername("沈丽连");
        userModel2.setMobile("13902348307");

        UserModel userModel1= new UserModel();
        userModel1.setUsername("赖茂龙");
        userModel1.setMobile("15815576007");

        List<UserModel> userModelList = new ArrayList<UserModel>();
        userModelList.add(userModel1);
        userModelList.add(userModel2);

        System.out.println(SensitiveInfoUtils.getSensitiveLog(userModelList));
    }

    }

