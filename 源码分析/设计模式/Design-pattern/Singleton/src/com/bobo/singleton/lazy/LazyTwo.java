package com.bobo.singleton.lazy;

/**
 * @program: Singleton
 * @description: ����ʽ������
 *
 * �������ⲿ��Ҫʹ�õ�ʱ��Ž���ʵ����
 *
 * �ŵ㣺
 *      1��д����
 *      2��ʹ�õ��Żᴴ��
 *      3���̰߳�ȫ
 *
 * ȱ�㣺���������synchronized���������ܻ�Ƚϲ�
 *
 * @author: bobobo
 * @create: 2018-07-07 07:16
 **/
public class LazyTwo {

    private LazyTwo() {
        System.out.println("LazyTwo ��ʼ��~~");
    }

    private static LazyTwo instance = null;

    //��synchronized
    public static synchronized LazyTwo getInstance() {
        if (instance == null) {
            instance = new LazyTwo();
        }
        return instance;
    }
}
