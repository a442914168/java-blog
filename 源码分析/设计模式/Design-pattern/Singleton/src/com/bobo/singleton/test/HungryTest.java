package com.bobo.singleton.test;

import com.bobo.singleton.hungry.Hungry;

/**
 * @program: Singleton
 * @description: ¶öººÊ½µ¥Àý²âÊÔ
 * @author: bobobo
 * @create: 2018-07-06 10:47
 **/
public class HungryTest implements SingetonTest {

    @Override
    public Object getInstance() {
        return Hungry.getInstance();
    }

    public static void main(String[] args) {
        ThreadSafeTest threadSafeTest = new ThreadSafeTest();
        threadSafeTest.start(new HungryTest());
    }
}
