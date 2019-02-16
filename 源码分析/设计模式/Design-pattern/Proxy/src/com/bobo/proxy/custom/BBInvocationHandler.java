package com.bobo.proxy.custom;

import java.lang.reflect.Method;

/**
 * @program: Proxy
 * @description: ģ��InvocationHandler�ӿ�
 * @author: bobobo
 * @create: 2018-07-11 11:22
 **/
public interface BBInvocationHandler {

    /**
     * �������ʵ���ϵķ������ò�����
     * @param proxy �������õĴ���ʵ��
     *              ��Ӧ��Demo����CustomMeipo��ֻ������ͨ���ֽ���������
     * @param method �ڴ���ʵ���ϵ��õĽӿڷ���
     *              ��Ӧ��Demo����findLove()��buy()������
     * @param args �ڴ���ʵ���ϵķ��������д��ݵĲ���
     * @return  ��ʵ���󷽷��ķ�������
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
