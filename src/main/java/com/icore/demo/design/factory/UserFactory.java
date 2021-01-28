package com.icore.demo.design.factory;

public interface UserFactory {

    /**
     * 水果工厂方法
     */
    public Fruit getFruit(Fruit whichFruit);
    /**
     * 肉的工厂方法
     */
    public Meat getMeat(Meat whichMeat);

}
