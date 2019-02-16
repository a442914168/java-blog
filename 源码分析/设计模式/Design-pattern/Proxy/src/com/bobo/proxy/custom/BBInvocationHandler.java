package com.bobo.proxy.custom;

import java.lang.reflect.Method;

/**
 * @program: Proxy
 * @description: 模拟InvocationHandler接口
 * @author: bobobo
 * @create: 2018-07-11 11:22
 **/
public interface BBInvocationHandler {

    /**
     * 处理代理实例上的方法调用并返回
     * @param proxy 方法调用的代理实例
     *              对应本Demo就是CustomMeipo，只不过是通过字节码重组后的
     * @param method 在代理实例上调用的接口方法
     *              对应本Demo就是findLove()，buy()方法。
     * @param args 在代理实例上的方法调用中传递的参数
     * @return  真实对象方法的返回类型
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
