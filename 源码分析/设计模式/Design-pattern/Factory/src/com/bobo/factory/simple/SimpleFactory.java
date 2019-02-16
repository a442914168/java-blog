package com.bobo.factory.simple;

import com.bobo.factory.Mengniu;
import com.bobo.factory.Milk;
import com.bobo.factory.Yili;

/**
 * @program: Factory
 * @description: �򵥹���
 * @author: bobobo
 * @create: 2018-07-04 06:42
 **/
public class SimpleFactory {

    /**
     * �������ƻ�ȡţ��
     * @param name ����
     * @return
     **/
    public Milk getMilk(String name) {
        if ("Mengniu".equalsIgnoreCase(name)) {
            return new Mengniu();
        } else if ("Yili".equalsIgnoreCase(name)) {
            return new Yili();
        } else {
            System.out.println("����Ϊ��ѽ����dei");
            return null;
        }
    }

}
