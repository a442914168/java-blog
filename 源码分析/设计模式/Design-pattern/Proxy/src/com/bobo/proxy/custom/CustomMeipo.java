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

        //�ֽ�������ԭ��
        //1���õ��������������ã�����ͨ�������ȡ���������еĽӿ�
        //2��BBProxy����������һ���µ��ࡢͬʱ�µ���Ҫʵ�ֱ�����������ʵ�ֵĽӿ�
        //3����̬����Java���룬���¼ӵ�ҵ���߼�������һ�����߼�����ȥ����
        //4�����������ɵ�Java����.class
        //5�������¼��ص�JVM������
        return BBProxy.newProxyInstance(new BBClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("��ã�����BOBO������ý��");
        System.out.println("��������Ҫɶ����");

        method.invoke(this.target, args);

        System.out.println("ok���յ���");

        return proxy;
    }
}
