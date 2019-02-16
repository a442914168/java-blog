package com.bobo.factory.simple;

import com.bobo.factory.Mengniu;
import com.bobo.factory.Milk;
import com.bobo.factory.Yili;

/**
 * @program: Factory
 * @description: 简单工厂
 * @author: bobobo
 * @create: 2018-07-04 06:42
 **/
public class SimpleFactory {

    /**
     * 根据名称获取牛奶
     * @param name 名称
     * @return
     **/
    public Milk getMilk(String name) {
        if ("Mengniu".equalsIgnoreCase(name)) {
            return new Mengniu();
        } else if ("Yili".equalsIgnoreCase(name)) {
            return new Yili();
        } else {
            System.out.println("无能为力呀，兄dei");
            return null;
        }
    }

}
