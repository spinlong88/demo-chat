package com.icore.demo.design.decorator;

//装饰器：为奶茶添加不同的口味
public class Taste implements MilkTea {

    @Override
    public String milkTeaName() {
        return "具体的名字让子类去定义";
    }
    @Override
    public int milkTeaPrice() {
        return 0;
    }

}
