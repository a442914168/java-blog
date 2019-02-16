package com.bobo.proxy.staticed;

import com.bobo.proxy.common.Zhangsan;

/**
 * @program: Proxy
 * @description: 静态代理测试
 * @author: bobobo
 * @create: 2018-07-10 20:55
 **/
public class StaticedTest {

    public static void main(String[] args) {
        //静态代理，必须要写明确已知的方法
        //这媒婆 不能帮人买东西
        Meipo meipo = new Meipo(new Zhangsan());
        meipo.findLove();
    }

}
