package com.icore.demo.design.strategy2;

public class TrainStrategy implements ITravelStrategy {
    @Override
    public void travelStyle() {
        System.out.println("乘坐火车");
    }
}
