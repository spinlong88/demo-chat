package com.icore.demo.design.strategy;

public class FileLog implements ILogStrategy {

    public void log(String msg) {
        System.out.println("现在把 '"+msg+"' 记录到文件中");
    }

}
