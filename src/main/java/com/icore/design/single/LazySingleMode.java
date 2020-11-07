package com.icore.design.single;

public class LazySingleMode {
    //懒汉模式（类加载时不初始化）
    //比较懒，在类加载时，不创建实例，因此类加载速度快，但运行时获取对象的速度慢
    //1）构造函数定义为私有----不能在别的类中来获取该类的对象，只能在类自身中得到自己的对象
    //2）成员变量为static的，没有初始化----类加载快，但访问类的唯一实例慢，static保证在自身类中获取自身对象
    //3）公开访问点getInstance： public和synchronized的-----public保证对外公开，同步保证多线程时的正确性（因为类变量不是在加载时初始化的）
    //优缺点见代码注释。
    private static LazySingleMode instance = null;//静态私用成员，没有初始化

    private LazySingleMode (){
        //私有构造函数
    }

    public static synchronized LazySingleMode getInstance(){
        if(instance == null){
            instance = new LazySingleMode();
        }
        return instance;
    }



}
