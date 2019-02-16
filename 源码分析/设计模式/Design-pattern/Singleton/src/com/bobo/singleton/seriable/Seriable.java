package com.bobo.singleton.seriable;

import java.io.Serializable;

/**
 * @program: Singleton
 * @description: 序列化防止破坏单例
 *
 *  序列化：
 *      将对象的状态信息转换为可以存储或传输的形式的过程
 *      从而转换一个IO流，写入到其他地方(可以是磁盘、网络IO)
 *      内存中状态给永久保存下来了
 *
 *  反序列化：
 *      将已经持久化的字节码内容，转换为IO流
 *      通过IO流的读取，进而将读取的内容转换为Java对象
 *      在转换过程中会重新创建对象new
 *
 *  解决办法：
 *      加入readResolve()
 *
 * @author: bobobo
 * @create: 2018-07-07 11:15
 **/
public class Seriable implements Serializable {

    private Seriable() {
        System.out.println("Seriable 初始化~~");
    }

    public final static Seriable instance = new Seriable();

    public static Seriable getInstance() {
        return instance;
    }

    //当JVM从内存中反序列化地"组装"一个新对象时，
    //就会自动调用这个readResolve() 来返回我们指定好的对象了，单例规则也就得到了保证。
    private Object readResolve() {
        return instance;
    }
}
