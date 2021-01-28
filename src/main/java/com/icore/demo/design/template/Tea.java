package com.icore.demo.design.template;

public class Tea extends RereshBaverage {

    @Override
    protected void brew() {
        System.out.println("用80 度的热水浸泡茶叶5分钟");
    }

    @Override
    protected void addCondiments() {
        System.out.println("加入柠檬");
    }

}
