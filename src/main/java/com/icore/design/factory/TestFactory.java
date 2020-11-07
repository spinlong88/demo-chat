package com.icore.design.factory;

public class TestFactory {

    public static void main(String[] args){
        //调用简单工厂模式
        //总结
        //A：我要购买苹果，只需向工厂角色（FruitFactory）请求即可。而工厂角色在接到请求后，会自行判断创建和提供哪一个产品。
        //B：但是对于工厂角色（FruitFactory ）来说，增加新的产品（比如说增加草莓）就是一个痛苦的过程。工厂角色必须知道每一种产品，如何创建它们，以及何时向客户端提供它们。换言之，接纳新的产品意味着修改这个工厂。
        //C：因此这种简单工厂模式的开放性比较差。
        EasyFruitFactory easyFruitFactory = new EasyFruitFactory();
        easyFruitFactory.getFruit("Orange").plantFruit();
        easyFruitFactory.getFruit("Apple").plantFruit();


        //调用工厂方法模式
        //总结：
        //A：工厂方法模式和简单工厂模式在结构上的不同是很明显的。工厂方法模式的核心是一个抽象工厂类，而简单工厂模式把核心放在一个具体类上。工厂方法模式可以允许很多具体工厂类从抽象工厂类中将创建行为继承下来，从而可以成为多个简单工厂模式的综合，进而推广了简单工厂模式。
        //B：工厂方法模式退化后可以变得很像简单工厂模式。设想如果非常确定一个系统只需要一个具体工厂类，那么就不妨把抽象工厂类合并到具体的工厂类中去。由于反正只有一个具体工厂类，所以不妨将工厂方法改成为静态方法，这时候就得到了简单工厂模式。
        //C：如果需要加入一个新的水果，那么只需要加入一个新的水果类以及它所对应的工厂类。没有必要修改客户端，也没有必要修改抽象工厂角色或者其他已有的具体工厂角色。对于增加新的水果类而言，这个系统完全支持"开-闭"原则。
       // D：对Factory Method模式而言，它只是针对一种类别（如本例中的水果类Fruit），但如果我们还想买肉，那就不行了，这是就必须要Abstract Factory模式帮忙了。
        MethodOrangeFactory methodOrangeFactory = new MethodOrangeFactory();
        methodOrangeFactory.getFruit().eatFruit();


        //调用抽象工厂模式
        //总结：
        //A：抽象工厂模式可以向客户端提供一个接口，使得客户端在不必指定产品的具体类型的情况下，创建多个产品族中的产品对象。这就是抽象工厂模式的用意。
        //B：抽象工厂模式是所有形态的工厂模式中最为抽象和最具一般性的一种形态。
        //：抽象工厂模式与工厂方法模式的最大区别就在于，工厂方法模式针对的是一个产品（Fruit）等级结构；而抽象工厂模式则需要面对多个产品等级结构(Fruit、Meat)。
        Fruit apple=new Apple();
        Meat bigMeat=new PigMeat();

        //消费者实例
        User me=new User();
        me.getFruit(apple).eatFruit();
        me.getMeat(bigMeat).buyMeat();
    }

}
