package com.icore.design.factory2;

public class GiftA implements Gift{

    @Override
    public String getGiftName() {
        return "GiftA";
    }

    @Override
    public int getPrice() {
        return 100;
    }

}
