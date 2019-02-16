package com.bobo.strategy;

/**
 * @program: Strategy
 * @description: ֧������Ժ��״̬
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
        return ("֧��״̬��[" + code + "]," + msg + ",�������飺" + data);
    }
}
