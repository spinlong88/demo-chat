package com.icore.demo.design.decorator;

public class UserTest {

    public static void main(String[] args) {
        MilkTea honeyMilkTea=new HoneyMilkTea();
        System.out.println("买了一杯蜂蜜奶茶，价格是"+honeyMilkTea.milkTeaPrice());
        honeyMilkTea = new AddCoffeeTaste(honeyMilkTea);
        System.out.println("加咖啡，价格是"+honeyMilkTea.milkTeaPrice());

        MilkTea pearlMilkTea=new PearlMilkTea();
        System.out.println("买了一杯珍珠奶茶，价格是"+pearlMilkTea.milkTeaPrice());
        pearlMilkTea = new AddIceTaste(pearlMilkTea);
        System.out.println("加冰块，价格是"+pearlMilkTea.milkTeaPrice());
    }

    //output
    //买了一杯蜂蜜奶茶，价格是20
    //加咖啡，价格是35
    //买了一杯珍珠奶茶，价格是20
    //加冰块，价格是25

}
