package com.bobo.factory.abstr;

import com.bobo.factory.Milk;

/**
 * @program: Factory
 * @description: ���󹤳�
 * @author: bobobo
 * @create: 2018-07-04 06:53
 **/
public abstract class AbstractFactory {

    /**
     * ��ȡһƿ��ţ
     * @return
     */
    public abstract Milk getMengniu();

    /**
     * ��ȡһƿ����
     * @return
     */
    public abstract Milk getYili();

}
