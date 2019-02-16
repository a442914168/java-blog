package com.bobo.singleton.test;

import com.bobo.singleton.lazy.LazyOne;

/**
 * @program: Singleton
 * @description: ����ʽһ����
 * @author: bobobo
 * @create: 2018-07-06 11:44
 **/
public class LazyOneTest implements SingetonTest {

    @Override
    public Object getInstance() {
        return LazyOne.getInstance();
    }

    public static void main(String[] args) {
        ThreadSafeTest threadSafeTest = new ThreadSafeTest();
        threadSafeTest.start(new LazyOneTest());
    }
}
