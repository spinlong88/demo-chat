package com.icore.demo.design.builder;

/**
 * Builder.java
 *  建造者
 */
abstract class Builder2 {
    //汉堡
    abstract Builder2 bulidA(String mes);
    //饮料
    abstract Builder2 bulidB(String mes);
    //薯条
    abstract Builder2 bulidC(String mes);
    //甜品
    abstract Builder2 bulidD(String mes);
    //获取套餐
    abstract Product2 build();
}