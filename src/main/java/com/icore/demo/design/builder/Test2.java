package com.icore.demo.design.builder;

/**
 * Test.java
 *  测试类
 */
public class Test2 {
    public static void main(String[] args) {
        ConcreteBuilder2 concreteBuilder = new ConcreteBuilder2();
        Product2 build = concreteBuilder
                .bulidA("牛肉煲")
//              .bulidC("全家桶")
                .bulidD("冰淇淋")
                .build();
        System.out.println(build.toString());
    }
}