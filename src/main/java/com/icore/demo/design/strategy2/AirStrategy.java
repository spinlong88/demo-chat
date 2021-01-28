package com.icore.demo.design.strategy2;

public class AirStrategy implements ITravelStrategy {
    @Override
    public void travelStyle() {
        System.out.println("乘坐飞机");
    }
}
