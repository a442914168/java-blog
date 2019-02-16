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

    //������Ķ��󣬰����ø���������
    private Person target;

    public Object getInstance(Person target) throws Exception {

        this.target = target;

        Class<?> clazz = target.getClass();

        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("����ý�ţ���Ҫ�����Ҷ��������Ѿ��õ��������");
        System.out.println("��ʼ��ɫ");

        //����ԭ����
        method.invoke(this.target, args);

        System.out.println("������ʵĻ�����׼������");

        return proxy;
    }
}
