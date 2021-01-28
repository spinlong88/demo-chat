package com.icore.demo.design.factory2;

public class ProductB implements Product {
    @Override
    public int getPrice() {
        return 200;
    }

    @Override
    public String getName() {
        return "ProductB";
    }

}
