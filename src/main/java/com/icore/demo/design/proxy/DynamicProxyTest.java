package com.icore.demo.design.proxy;

import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    public static void main(String[] args) {
                 BuyHouse buyHouse = new BuyHouseImpl();
                 BuyHouse proxyBuyHouse = (BuyHouse) Proxy.newProxyInstance(BuyHouse.class.getClassLoader(), new
                                 Class[]{BuyHouse.class}, new DynamicProxyHandler(buyHouse));
                 proxyBuyHouse.buyHosue();
             }
}
