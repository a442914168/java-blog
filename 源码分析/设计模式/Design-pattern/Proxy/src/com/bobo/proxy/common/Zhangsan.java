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
        System.out.println("��Ҫ����");
    }

    @Override
    public void buy() {
        System.out.println("��ҪiPhone X");
    }
}
