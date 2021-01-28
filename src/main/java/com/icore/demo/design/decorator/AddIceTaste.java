package com.icore.demo.design.decorator;

public class AddIceTaste extends Taste {

    private String description = "奶茶加冰。。。。";
    private MilkTea milkTea=null;

    public AddIceTaste( MilkTea milkTea) {
        this.milkTea = milkTea;
    }
    //我们让相应的奶茶去增加这个口味
    public String milkTeaName() {
        return milkTea.milkTeaName()+" "+description;
    }
    //同理，奶茶的价格也要提高一点:增加5块钱吧
    public int milkTeaPrice() {
        return milkTea.milkTeaPrice()+5;
    }

}
