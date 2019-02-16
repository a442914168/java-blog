package com.bobo.strategy;

/**
 * @program: Strategy
 * @description: 支付完成以后的状态
 * @author: bobobo
 * @create: 2018-07-14 12:21
 **/
public class PayState {

    private int code;
    private Object data;
    private String msg;

    public PayState(int code, String msg,Object data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public String toString(){
        return ("支付状态：[" + code + "]," + msg + ",交易详情：" + data);
    }
}
