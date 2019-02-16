package com.bobo.singleton.test;

import com.bobo.singleton.lazy.LazyFour;

import java.lang.reflect.Constructor;

/**
 * @program: Singleton
 * @description: �������
 * @author: bobobo
 * @create: 2018-07-07 09:49
 **/
public class ReflectTest {

    public static void main(String[] args) {
        try {

            Class<?> clazz = LazyFour.class;

            //ͨ�������õ�˽�еĹ��췽��
            Constructor c = clazz.getDeclaredConstructor(null);
            //ǿ�Ʒ���
            c.setAccessible(true);

            //������ʼ��
            Object o1 = c.newInstance();

            //���������ι��췽�����൱��new������
            //����ԭ�������⣬
            Object o2 = c.newInstance();

            System.out.println(o1 == o2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
