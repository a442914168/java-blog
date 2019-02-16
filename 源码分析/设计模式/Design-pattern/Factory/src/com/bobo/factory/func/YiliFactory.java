package com.bobo.factory.func;

import com.bobo.factory.Milk;
import com.bobo.factory.Yili;

/**
 * @program: Factory
 * @description: ��������
 * @author: bobobo
 * @create: 2018-07-04 06:50
 **/
public class YiliFactory implements Factory {

    @Override
    public Milk getMilk() {
        return new Yili();
    }
}
