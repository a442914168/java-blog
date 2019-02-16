package com.bobo.proxy.cglib;

import com.bobo.proxy.common.Zhangsan;

/**
 * @program: Proxy
 * @description:
 * @author: bobobo
 * @create: 2018-07-10 22:39
 **/
public class CglibTest {

    public static void main(String[] args) {
        try {
            Lisi lisi = (Lisi) new CglibMeipo().getInstance(Lisi.class);
            lisi.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
