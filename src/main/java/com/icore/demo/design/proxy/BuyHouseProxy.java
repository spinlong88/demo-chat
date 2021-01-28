package com.icore.demo.design.proxy;

/**
 * 优点：可以做到在符合开闭原则的情况下对目标对象进行功能扩展。
 * 缺点：我们得为每一个服务都得创建代理类，工作量太大，不易管理。同时接口一旦发生改变，代理类也得相应修改。
 */
public class BuyHouseProxy implements BuyHouse {

    private BuyHouse buyHouse;

    public BuyHouseProxy(final BuyHouse buyHouse) {
                 this.buyHouse = buyHouse;
    }

    @Override
    public void buyHosue() {
       System.out.println("买房前准备");
        buyHouse.buyHosue();
        System.out.println("买房后装修");
    }

}
