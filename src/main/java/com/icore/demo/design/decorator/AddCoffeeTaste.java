package com.icore.demo.design.decorator;

public class AddCoffeeTaste extends Taste {

    private String description = "奶茶加咖啡。。。。";
    private MilkTea milkTea=null;

    public AddCoffeeTaste( MilkTea milkTea) {
        this.milkTea = milkTea;
    }
    //我们让相应的奶茶去增加这个口味
    public String milkTeaName() {
        return milkTea.milkTeaName()+" "+description;
    }
    //同理，奶茶的价格也要提高一点:增加15块钱吧
    public int milkTeaPrice() {
        return milkTea.milkTeaPrice()+15;
    }

}
