package com.icore.demo.design.builder;

/**
 * Test.java
 *  测试类
 */
public class Test {
    public static void main(String[] args) {
        Director director = new Director();
        Product create = director.create(new ConcreteBuilder());
        System.out.println(create.toString());
    }
}
