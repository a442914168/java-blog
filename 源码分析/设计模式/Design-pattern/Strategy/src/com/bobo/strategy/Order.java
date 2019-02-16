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
     * if/switch��ʽ��������չ��ÿ����Ӷ���Ҫ�޸ķ���
     *
     * @param paymentType
     * @return
     */
    public PayState simplePay(String paymentType) {

        if ("֧����".equalsIgnoreCase(paymentType)) {
            System.out.println("����֧����֧��...");
            return new PayState(200, "֧���ɹ�", this.amount);
        } else if ("΢��".equalsIgnoreCase(paymentType)) {
            System.out.println("����΢��֧��...");
            return new PayState(200, "֧���ɹ�", this.amount);
        }

        return null;
    }

    /**
     * ʹ�ò���ģʽ֧����������������ʱ�򣬾Ͳ���Ҫ���if/swit
     * @param payType
     * @return
     */
    public PayState strategyPay(PayType payType) {
        return payType.get().pay(this.uid,this.amount);
    }

}
