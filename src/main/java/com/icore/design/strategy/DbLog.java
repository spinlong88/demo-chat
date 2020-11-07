package com.icore.design.strategy;

public class DbLog implements ILogStrategy {
    @Override
    public void log(String msg) {
        System.out.println("现在把 '"+msg+"' 记录到数据库中");
    }
}
