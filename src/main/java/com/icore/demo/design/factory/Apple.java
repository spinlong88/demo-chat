package com.icore.demo.design.factory;

//苹果实现水果接口
public class Apple implements Fruit {

    @Override
    public void plantFruit() {
        System.out.println("种苹果。");
    }

    @Override
    public void eatFruit() {
        System.out.println("吃苹果。");
    }
}
