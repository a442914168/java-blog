package com.bobo.singleton.test;

import com.bobo.singleton.lazy.LazyOne;
import com.bobo.singleton.lazy.LazyTwo;

/**
 * @program: Singleton
 * @description: ����ʽ����������
 * @author: bobobo
 * @create: 2018-07-07 07:21
 **/
public class LazyTwoTest {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
//            LazyOne.getInstance();
            LazyTwo.getInstance();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("�ܺ�ʱ: " + (endTime - startTime));
    }

}
