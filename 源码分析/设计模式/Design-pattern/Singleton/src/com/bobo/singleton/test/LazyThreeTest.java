package com.bobo.singleton.test;

import com.bobo.singleton.lazy.LazyThree;

/**
 * @program: Singleton
 * @description: ÀÁººÊ½µ¥ÀıÈı²âÊÔ
 * @author: bobobo
 * @create: 2018-07-07 10:05
 **/
public class LazyThreeTest implements SingetonTest {

    @Override
    public Object getInstance() {
        return LazyThree.getInstance();
    }

    public static void main(String[] args) {
        ThreadSafeTest threadSafeTest = new ThreadSafeTest();
        threadSafeTest.start(new LazyThreeTest());
    }
}
