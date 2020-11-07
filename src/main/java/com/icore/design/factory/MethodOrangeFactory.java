package com.icore.design.factory;

public class MethodOrangeFactory implements MethodFruitFactory {


    @Override
    public Fruit getFruit() {
        return new Orange();
    }
}
