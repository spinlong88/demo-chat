package com.icore.demo.design.factory;

public class PigMeat implements Meat {

    @Override
    public void buyMeat() {
        System.out.println("买猪肉。");
    }

    @Override
    public void eatMeat() {
        System.out.println("吃猪肉。");
    }

}
