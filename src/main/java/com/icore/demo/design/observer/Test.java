package com.icore.demo.design.observer;

public class Test {



    public static void main(String[] args) {
        RealSubject subject = new RealSubject();
        RealObserver observer = new RealObserver();
        subject.addObserver(observer);
        subject.makeChanged();
    }

}
