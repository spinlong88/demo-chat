package com.icore.demo.design.factory;

public class CowMeat implements Meat {

    @Override
    public void buyMeat() {
        System.out.println("买牛肉。");
    }

    @Override
    public void eatMeat() {
        System.out.println("吃牛肉。");
    }
}
