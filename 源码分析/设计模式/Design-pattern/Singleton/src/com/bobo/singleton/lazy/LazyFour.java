package com.bobo.singleton.lazy;

/**
 * @program: Singleton
 * @description: 懒汉式单例三
 *
 *  特点：
 *      在外部类被调用的时候内部类才会被加载
 *      内部类一定是要在调用之前初始化
 *      巧妙的避免了线程安全问题
 *      这种形式兼顾饿汉式的内存浪费，也兼顾synchronized性能问题
 *      可以说是最牛逼的写法了
 *
 * @author: bobobo
 * @create: 2018-07-07 07:26
 **/
public class LazyFour {

    //标志是否初始化，防止反射重复初始化
    private static boolean initialized = false;

    //默认使用LazyThree的时候，会先初始化内部类
    //如果没使用的话，内部类是不加载的
    private LazyFour() {
        synchronized (LazyFour.class) {
            if (initialized == false) {
                initialized = true;
                System.out.println("LazyFour 初始化~~");
            } else {
                throw new RuntimeException("LazyFour 单例已存在~");
            }
        }
    }

    //每一个关键字都不是多余的
    //static 是为了使单例的空间共享
    //final 保证这个方法不会被重写，重载
    public static final LazyFour getInstance() {
        //在返回结果以前，一定会先加载内部类
        return LazyHolder.LAZY;
    }

    //默认不加载,访问的时候才加载
    private static class LazyHolder {
        private static final LazyFour LAZY = new LazyFour();
    }

}
