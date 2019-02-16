package com.bobo.strategy.payPort;

/**
 * @program: Strategy
 * @description: ÷ß∏∂¿‡–Õ
 * @author: bobobo
 * @create: 2018-07-14 20:03
 **/
public enum PayType {

    ALI_PAY(new ALIPay()),
    WeXin_PAY(new WeXinPay());

    private Payment payment;

    PayType (Payment payment) {
        this.payment = payment;
    }

    public Payment get() {
        return this.payment;
    }

}
