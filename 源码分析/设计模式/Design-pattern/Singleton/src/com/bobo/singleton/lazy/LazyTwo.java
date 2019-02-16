package com.bobo.singleton.lazy;

/**
 * @program: Singleton
 * @description: 懒汉式单例二
 *
 * 它是在外部需要使用的时候才进行实例化
 *
 * 优点：
 *      1、写法简单
 *      2、使用到才会创建
 *      3、线程安全
 *
 * 缺点：由于添加锁synchronized，所以性能会比较差
 *
 * @author: bobobo
 * @create: 2018-07-07 07:16
 **/
public class LazyTwo {

    private LazyTwo() {
        System.out.println("LazyTwo 初始化~~");
    }

    private static LazyTwo instance = null;

    //加synchronized
    public static synchronized LazyTwo getInstance() {
        if (instance == null) {
            instance = new LazyTwo();
        }
        return instance;
    }
}
