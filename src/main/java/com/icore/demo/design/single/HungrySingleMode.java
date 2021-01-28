package com.icore.demo.design.single;

public class HungrySingleMode {

    //饿汉式单例模式（在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度快）
    //静态私有成员，已初始化
    private static HungrySingleMode instance = new HungrySingleMode();

    private HungrySingleMode(){
        //私有构造函数
    }

    //静态，不用同步（类加载时已初始化，不会有多线程的问题）
    public static HungrySingleMode getInstance(){
        return instance;
    }

}
