package com.bobo.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: Proxy
 * @description:
 * @author: bobobo
 * @create: 2018-07-10 22:36
 **/
public class CglibMeipo implements MethodInterceptor {

    public Object getInstance(Class<?> clazz) throws Exception {

        Enhancer enhancer = new Enhancer();
        //Ҫ���ĸ�����Ϊ�������ɵ����ุ��
        enhancer.setSuperclass(clazz);
        //����ִ�з���Ϊ��ǰ
        enhancer.setCallback(this);

        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("����ý�ţ���Ҫ�����Ҷ��������Ѿ��õ��������");
        System.out.println("��ʼ��ɫ");

        methodProxy.invokeSuper(o, objects);

        System.out.println("������ʵĻ�����׼������");

        return o;
    }
}
