package com.icore.design.factory2;

public class GiftB implements Gift{

    @Override
    public String getGiftName() {
        return "GiftB";
    }

    @Override
    public int getPrice() {
        return 200;
    }

}
