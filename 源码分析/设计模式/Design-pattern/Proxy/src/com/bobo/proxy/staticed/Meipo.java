package com.bobo.proxy.staticed;

import com.bobo.proxy.common.Person;

/**
 * @program: Proxy
 * @description:
 * @author: bobobo
 * @create: 2018-07-10 21:10
 **/
public class Meipo {

    private Person person;

    public Meipo(Person person) {
        this.person = person;
    }

    /**
     * ����
     */
    public void findLove() {
        System.out.println("��Ҫ��ɶ����");
        person.findLove();
        System.out.println("û�У��Լ�newһ��ȥ");
    }

}
