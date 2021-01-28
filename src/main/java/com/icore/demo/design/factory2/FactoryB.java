package com.icore.demo.design.factory2;

public class FactoryB implements IFactory{

    @Override
    public Product createProduct() {
        return new ProductB();
    }

    @Override
    public Gift createGift() {
        return new GiftB();
    }
    
}
