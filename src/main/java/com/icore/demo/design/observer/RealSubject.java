package com.icore.demo.design.observer;

import java.util.Observable;

public class RealSubject extends Observable {

    public void makeChanged() {
        setChanged();
        notifyObservers();
    }

}
