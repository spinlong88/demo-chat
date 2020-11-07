package com.icore.design.builder;

/**
 * ConcreteBuilder.java
 *  具体建造者(服务员)
 */
public class ConcreteBuilder2 extends Builder2{
    private Product2 product;
    public ConcreteBuilder2() {
        product = new Product2();
    }

    @Override
    Product2 build() {
        return product;
    }

    @Override
    Builder2 bulidA(String mes) {
        product.setBuildA(mes);
        return this;
    }

    @Override
    Builder2 bulidB(String mes) {
        product.setBuildB(mes);
        return this;
    }

    @Override
    Builder2 bulidC(String mes) {
        product.setBuildC(mes);
        return this;
    }

    @Override
    Builder2 bulidD(String mes) {
        product.setBuildD(mes);
        return this;
    }
}
