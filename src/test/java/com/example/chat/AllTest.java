package com.example.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icore.model.UserModel;
import com.icore.sensitive.ValueDesensitizeFilter;
import com.icore.sensitive.demo.JavaBeanA;
import com.icore.sensitive.demo.JavaBeanB;

import java.util.*;

public class AllTest {

    public static void main(String[] args) {
        UserModel userModel2 = new UserModel();
        userModel2.setMobile("15815576007");
        UserModel userModel1 = new UserModel();
        userModel1.setMobile("15815576007");
        List<UserModel> userModelList = new ArrayList<UserModel>();
        userModelList.add(userModel1);
        userModelList.add(userModel2);

        Map<String, UserModel> a1m2 = new HashMap<String, UserModel>();
        a1m2.put("sea1",userModel1);
        a1m2.put("sea2",userModel2);

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


//        userModelList = SensitiveUtil.desCopy(userModelList);
////        System.out.println("脱敏对象： " + userModelList);
////        System.out.println("脱敏后原始： " + userModelList);

        System.out.println(JSON.toJSONString(a1, SerializerFeature.DisableCircularReferenceDetect));
        System.out.println(JSON.toJSONString(a1, new ValueDesensitizeFilter(),SerializerFeature.DisableCircularReferenceDetect));
        System.out.println(JSON.toJSONString(JSON.toJSON(a1), new ValueDesensitizeFilter()));
        System.out.println(JSON.toJSON(a1));
    }

}
