package com.bobo.singleton.lazy;

/**
 * @program: Singleton
 * @description: ����ʽ����һ
 *
 *  �������ⲿ��Ҫʹ�õ�ʱ��Ž���ʵ����
 *
 *  �ŵ㣺
 *      1��д����
 *      2��ʹ�õ��Żᴴ��
 *
 *  ȱ�㣺����߳�ͬʱ���ã��ᴴ�����ʵ��
 *
 * @author: bobobo
 * @create: 2018-07-06 11:23
 **/
public class LazyOne {

    private LazyOne() {
        System.out.println("LazyOne ��ʼ��~~");
    }

    private static LazyOne instance = null;

    public static LazyOne getInstance() {

        //���÷���֮ǰ�����ж����޳�ʼ��
        //���û��ʼ�����ȳ�ʼ�������Ҹ�ֵ
        //
        //�����п���2���߳�ͬʱ��ͬʱ��ͬʱ �ж�Ϊ�գ���ʱ�ͻᴴ��2��ʵ��

        if (instance == null) {
            instance = new LazyOne();
        }
        return instance;
    }

}
