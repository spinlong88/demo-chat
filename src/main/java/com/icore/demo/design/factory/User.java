package com.icore.demo.design.factory;

public class User implements UserFactory {

    @Override
    public Fruit getFruit(Fruit whichFruit) {
        return whichFruit;
    }

    @Override
    public Meat getMeat(Meat whichMeat) {
        return whichMeat;
    }

}
