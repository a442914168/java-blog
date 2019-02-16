package com.bobo.factory.abstr;

import com.bobo.factory.Milk;
import com.bobo.factory.func.MengniuFactory;
import com.bobo.factory.func.YiliFactory;

/**
 * @program: Factory
 * @description:
 * @author: bobobo
 * @create: 2018-07-04 06:55
 **/
public class MilkFactory extends AbstractFactory {

    @Override
    public Milk getMengniu() {
        return new MengniuFactory().getMilk();
    }

    @Override
    public Milk getYili() {
        return new YiliFactory().getMilk();
    }
}
