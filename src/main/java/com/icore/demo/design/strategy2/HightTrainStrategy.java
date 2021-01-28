package com.icore.demo.design.strategy2;

public class HightTrainStrategy implements ITravelStrategy {
    @Override
    public void travelStyle() {
        System.out.println("乘坐高铁");
    }
}
