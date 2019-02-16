package com.bobo.factory.func;

import com.bobo.factory.Milk;

/**
 * @program: Factory
 * @description: 工厂模型
 * @author: bobobo
 * @create: 2018-07-04 06:49
 **/
public interface Factory {

    /**
     * 工厂的通用技能
     * @return
     */
    public Milk getMilk();

}
