package com.icore.design.decorator;

public class PearlMilkTea implements MilkTea{

    @Override
    public String milkTeaName() {
        return "珍珠奶茶";
    }
    //珍珠奶茶一杯15块
    @Override
    public int milkTeaPrice() {
        return 15;
    }

}
