package com.icore.design.factory2;

public class FactoryA implements IFactory{

    @Override
    public Product createProduct() {
        return new ProductA();
    }

    @Override
    public Gift createGift() {
        return new GiftA();
    }

}
