package com.bobo.factory.func;

/**
 * @program: Factory
 * @description: ≤‚ ‘¿‡
 * @author: bobobo
 * @create: 2018-07-04 06:51
 **/
public class FactoryTest {

    public static void main(String[] args) {
        Factory factory = new MengniuFactory();
        System.out.println(factory.getMilk());

        factory = new YiliFactory();
        System.out.println(factory.getMilk());
    }

}
