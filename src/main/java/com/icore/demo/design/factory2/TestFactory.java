package com.icore.demo.design.factory2;

public class TestFactory {

    public static void main(String[] args){
        //总结： 简单工厂模式最大的优点在于工厂类中包含了必要的逻辑判断，根据客户端的选择条件动态实例化相关的类，
        // 对于客户端来说，去除了与具体产品的依赖。但是当需求变动的时候，需要对原有的类进行修改，违背了开放封闭原则。

        Product productA = Factory.createProduct("A");
        System.out.println("简单工厂：productA name="+productA.getName()+",getPrice:"+productA.getPrice());

        Product productB = Factory.createProduct("B");
        System.out.println("简单工厂：productB name="+productB.getName()+",getPrice:"+productB.getPrice());


        IFactory factoryA = new FactoryA();
        productA = factoryA.createProduct();
        System.out.println("工厂方法：productA name="+productA.getName()+",getPrice:"+productA.getPrice());

        IFactory factoryB = new FactoryB();
        productB = factoryB.createProduct();
        System.out.println("工厂方法：productB name="+productB.getName()+",getPrice:"+productB.getPrice());

        //总结：由于使用了多态，工厂方法克服了简单工厂违背的开放封闭原则的缺点，又保持了封装对象创建过程的优点。


        factoryA = new FactoryA();
        productA = factoryA.createProduct();
        Gift giftA = factoryA.createGift();
        System.out.println("抽象工厂：productA name="+productA.getName()+",getPrice:"+productA.getPrice());
        System.out.println("抽象工厂：giftA name="+giftA.getGiftName()+",getPrice:"+giftA.getPrice());

        // 总结：抽象工厂模式提供一个创建一系列相关或相互依赖对象的接口，而无需制定他们具体的类。
        // 抽象工厂接口，应该包含所有的产品创建的抽象方法，我们可以定义实现不止一个接口，一个工厂也可以生产不止一种产品类，
        // 和工厂方法模式一样，抽象工厂模式同样实现了开发封闭原则

    }


}
