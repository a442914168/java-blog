package com.bobo.factory.abstr;

/**
 * @program: Factory
 * @description: ≥ÈœÛπ§≥ß≤‚ ‘
 * @author: bobobo
 * @create: 2018-07-04 06:56
 **/
public class AbstractFactoryTest {

    public static void main(String[] args) {
        MilkFactory milkFactory = new MilkFactory();
        System.out.println(milkFactory.getMengniu());
        System.out.println(milkFactory.getYili());
    }

}
