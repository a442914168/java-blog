package com.bobo.strategy;

import com.bobo.strategy.payPort.PayType;

/**
 * @program: Strategy
 * @description:
 * @author: bobobo
 * @create: 2018-07-14 20:17
 **/
public class Order {

    private String uid;
    private String orderId;
    private double amount;

    public Order(String uid, String orderId, double amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    /**
     * if/switch方式，不易拓展，每次添加都需要修改方法
     *
     * @param paymentType
     * @return
     */
    public PayState simplePay(String paymentType) {

        if ("支付宝".equalsIgnoreCase(paymentType)) {
            System.out.println("操作支付宝支付...");
            return new PayState(200, "支付成功", this.amount);
        } else if ("微信".equalsIgnoreCase(paymentType)) {
            System.out.println("操作微信支付...");
            return new PayState(200, "支付成功", this.amount);
        }

        return null;
    }

    /**
     * 使用策略模式支付，在新增方法的时候，就不需要添加if/swit
     * @param payType
     * @return
     */
    public PayState strategyPay(PayType payType) {
        return payType.get().pay(this.uid,this.amount);
    }

}
