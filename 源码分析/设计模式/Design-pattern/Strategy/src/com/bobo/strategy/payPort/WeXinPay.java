package com.bobo.strategy.payPort;

import com.bobo.strategy.PayState;

/**
 * @program: Strategy
 * @description:
 * @author: bobobo
 * @create: 2018-07-14 20:01
 **/
public class WeXinPay implements Payment {

    @Override
    public PayState pay(String uid, double amount) {
        System.out.println("操作微信支付...");
        return new PayState(200,"支付成功",amount);
    }
}
