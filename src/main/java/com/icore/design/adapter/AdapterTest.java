package com.icore.design.adapter;

public class AdapterTest {

    public static void main(String[] args){
        //测试
        ClassAdapter adapter = new ClassAdapter();
        adapter.method1();
        adapter.method2();

        ObjectAdapter objectAdapter = new ObjectAdapter(new Adaptee());
        objectAdapter.method1();
        objectAdapter.method2();
    }

}
