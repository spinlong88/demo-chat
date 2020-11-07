package com.icore.design.factory;

public class EasyFruitFactory {

    public static Fruit getFruit(String fruitName){
        //简单工厂模式
        if (fruitName.equalsIgnoreCase("Apple")) { // 如果是苹果，则返回苹果实例
            return new Apple();
        } else if (fruitName.equalsIgnoreCase("Orange")) { // 如果是橘子，则返回橘子实例
            return new Orange();
        } else {
            return null;
        }
    }

}
