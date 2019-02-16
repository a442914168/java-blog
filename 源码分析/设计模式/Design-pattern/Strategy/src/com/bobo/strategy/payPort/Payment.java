package com.bobo.strategy.payPort;

import com.bobo.strategy.PayState;

/**
 * @program: Strategy
 * @description:    Ö§¸¶ÇþµÀ
 * @author: bobobo
 * @create: 2018-07-14 20:01
 **/
public interface Payment {

    public PayState pay(String uid, double amount);

}
