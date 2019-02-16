package com.bobo.proxy.custom;

import com.bobo.proxy.common.Person;
import java.lang.reflect.Method;

/**
 * @program: Proxy
 * @description:
 * @author: bobobo
 * @create: 2018-07-11 11:26
 **/
public class CustomMeipo implements BBInvocationHandler {

    private Person target;

    public Object getInstance(Person target) throws Exception {
        this.target = target;

        Class<?> clazz = target.getClass();

        //字节码重组原理：
        //1、拿到被代理对象的引用，并且通过反射获取到它的所有的接口
        //2、BBProxy类重新生成一个新的类、同时新的类要实现被代理类所有实现的接口
        //3、动态生成Java代码，把新加的业务逻辑方法由一定的逻辑代码去调用
        //4、编译新生成的Java代码.class
        //5、再重新加载到JVM中运行
        return BBProxy.newProxyInstance(new BBClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("你好，我是BOBO派来的媒婆");
        System.out.println("请问你需要啥对象");

        method.invoke(this.target, args);

        System.out.println("ok，收到！");

        return proxy;
    }
}
