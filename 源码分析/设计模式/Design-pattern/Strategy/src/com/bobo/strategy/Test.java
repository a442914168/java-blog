package com.bobo.strategy;

import com.bobo.strategy.payPort.PayType;

/**
 * @program: Strategy
 * @description:
 * @author: bobobo
 * @create: 2018-07-14 20:25
 **/
public class Test {

    public static void main(String[] args) {

        Order order = new Order("bobobo", "20180311001000009", 200);

//        simpleTest(order);
        strategyTest(order);
    }

    public static void simpleTest(Order order) {
        System.out.println(order.simplePay("Ö§¸¶±¦"));
    }

    public static void strategyTest(Order order) {
        System.out.println(order.strategyPay(PayType.ALI_PAY));
    }
}
