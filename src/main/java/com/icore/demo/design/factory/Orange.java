package com.icore.demo.design.factory;

public class Orange implements Fruit {


    @Override
    public void plantFruit() {
        System.out.println("种橘子。");
    }

    @Override
    public void eatFruit() {
        System.out.println("吃橘子。");
    }
}
