package com.icore.demo.design.factory;

public class MethodAppleFactory implements MethodFruitFactory {

    @Override
    public Fruit getFruit() {
        return new Apple();
    }
}
