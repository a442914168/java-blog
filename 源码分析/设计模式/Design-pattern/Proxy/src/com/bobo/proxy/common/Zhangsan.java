package com.bobo.proxy.common;

/**
 * @program: Proxy
 * @description:
 * @author: bobobo
 * @create: 2018-07-10 21:07
 **/
public class Zhangsan implements Person {

    @Override
    public void findLove() {
        System.out.println("我要大长腿");
    }

    @Override
    public void buy() {
        System.out.println("我要iPhone X");
    }
}
