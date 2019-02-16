package com.bobo.proxy.jdk;

import com.bobo.proxy.common.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: Proxy
 * @description: JdkMeipo
 * @author: bobobo
 * @create: 2018-07-10 21:31
 **/
public class JDKMeipo implements InvocationHandler {

    //被代理的对象，把引用给保存下来
    private Person target;

    public Object getInstance(Person target) throws Exception {

        this.target = target;

        Class<?> clazz = target.getClass();

        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("我是媒婆：我要给你找对象，现在已经拿到你的需求");
        System.out.println("开始物色");

        //调用原方法
        method.invoke(this.target, args);

        System.out.println("如果合适的话，就准备办事");

        return proxy;
    }
}
