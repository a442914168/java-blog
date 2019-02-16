package com.bobo.factory.abstr;

import com.bobo.factory.Milk;

/**
 * @program: Factory
 * @description: 抽象工厂
 * @author: bobobo
 * @create: 2018-07-04 06:53
 **/
public abstract class AbstractFactory {

    /**
     * 获取一瓶蒙牛
     * @return
     */
    public abstract Milk getMengniu();

    /**
     * 获取一瓶伊利
     * @return
     */
    public abstract Milk getYili();

}
