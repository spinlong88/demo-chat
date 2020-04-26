package com.icore.util;

public class MicroLogFactory {

    public static MicroLogUtil getLooger(){
       return MicroLogUtil.getInstance();
    }
}
