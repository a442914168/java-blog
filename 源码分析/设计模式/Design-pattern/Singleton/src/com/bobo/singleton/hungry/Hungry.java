package com.bobo.singleton.hungry;

/**
 * @program: Singleton
 * @description: 饿汉式单例
 *
 *  它是在类加载的时候就立即初始化！
 *
 *  优点：
 *      1、没有加任何的锁，执行效率比较高。
 *      2、绝对的线程安全，因为线程还没出现的时候，就实例化了，不存在访问安全问题。
 *
 *  缺点：
 *      类加载的时候就初始化，不管你用还是不用。
 *
 * @author: bobobo
 * @create: 2018-07-06 10:07
 **/
public class Hungry {

    private Hungry() {
        System.out.println("Hungry 初始化~~");
    }

    //1、static
    //  静态变量被所有的对象所共享，在内存中只有一个副本，它当且仅当在类初次加载时会被初始化。
    //
    //2、final
    //  一旦你将引用声明作final，你将不能改变这个引用了，编译器会检查代码，如果你试图将变量再次初始化的话，编译器会报编译错误。
    //
    private static final Hungry hungry = new Hungry();

    public static Hungry getInstance() {
        return hungry;
    }

}
