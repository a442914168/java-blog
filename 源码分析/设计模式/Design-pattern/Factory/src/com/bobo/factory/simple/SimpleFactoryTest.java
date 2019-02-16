package com.bobo.factory.simple;

import com.bobo.factory.Milk;

/**
 * @program: Factory
 * @description: 简单工厂测试类
 * @author: bobobo
 * @create: 2018-07-04 06:45
 **/
public class SimpleFactoryTest {

    public static void main(String[] args) {
        Milk milk = new SimpleFactory().getMilk("mengniu");
        System.out.println(milk.getName());
    }

}
