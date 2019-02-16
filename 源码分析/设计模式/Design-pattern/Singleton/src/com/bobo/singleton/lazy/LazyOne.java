package com.bobo.singleton.lazy;

/**
 * @program: Singleton
 * @description: 懒汉式单例一
 *
 *  它是在外部需要使用的时候才进行实例化
 *
 *  优点：
 *      1、写法简单
 *      2、使用到才会创建
 *
 *  缺点：多个线程同时调用，会创建多个实例
 *
 * @author: bobobo
 * @create: 2018-07-06 11:23
 **/
public class LazyOne {

    private LazyOne() {
        System.out.println("LazyOne 初始化~~");
    }

    private static LazyOne instance = null;

    public static LazyOne getInstance() {

        //调用方法之前，先判断有无初始化
        //如果没初始化，先初始化，并且赋值
        //
        //但是有可能2个线程同时，同时，同时 判断为空，这时就会创建2个实例

        if (instance == null) {
            instance = new LazyOne();
        }
        return instance;
    }

}
