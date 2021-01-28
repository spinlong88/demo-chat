package com.icore.demo.design.decorator;

public class HoneyMilkTea implements MilkTea {

    @Override
    public String milkTeaName() {
        return "蜂蜜奶茶";
    }
    //蜂蜜奶茶一杯20块
    @Override
    public int milkTeaPrice() {
        return 20;
    }
}
