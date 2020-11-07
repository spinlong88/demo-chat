package com.icore.design.strategy2;

import com.icore.filter.TraceIdHelper;

public class Traveler {
    //出行策略接口
    ITravelStrategy travelStrategy;

    //设置出行策略
    public void setTravelStrategy(ITravelStrategy travelStrategy){
        this.travelStrategy = travelStrategy;
    }

    //为当前用户设置出行方式
    public void travelStyle(){
        travelStrategy.travelStyle();
    }

    public static void main(String[] args){
        Traveler traveler = new Traveler();
        //游客设置出行策略
        traveler.setTravelStrategy(new TrainStrategy());
        traveler.setTravelStrategy(new HightTrainStrategy());
        traveler.setTravelStrategy(new AirStrategy());
        //游客出行
        traveler.travelStyle();

    }

}
