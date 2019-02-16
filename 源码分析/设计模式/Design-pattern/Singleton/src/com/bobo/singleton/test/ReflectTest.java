package com.bobo.singleton.test;

import com.bobo.singleton.lazy.LazyFour;

import java.lang.reflect.Constructor;

/**
 * @program: Singleton
 * @description: 反射测试
 * @author: bobobo
 * @create: 2018-07-07 09:49
 **/
public class ReflectTest {

    public static void main(String[] args) {
        try {

            Class<?> clazz = LazyFour.class;

            //通过反射拿到私有的构造方法
            Constructor c = clazz.getDeclaredConstructor(null);
            //强制访问
            c.setAccessible(true);

            //暴力初始化
            Object o1 = c.newInstance();

            //调用了两次构造方法，相当于new了两次
            //犯了原则性问题，
            Object o2 = c.newInstance();

            System.out.println(o1 == o2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
