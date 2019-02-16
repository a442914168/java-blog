package com.bobo.factory.func;

import com.bobo.factory.Mengniu;
import com.bobo.factory.Milk;

/**
 * @program: Factory
 * @description: √…≈£π§≥ß
 * @author: bobobo
 * @create: 2018-07-04 06:50
 **/
public class MengniuFactory implements Factory {

    @Override
    public Milk getMilk() {
        return new Mengniu();
    }
}
