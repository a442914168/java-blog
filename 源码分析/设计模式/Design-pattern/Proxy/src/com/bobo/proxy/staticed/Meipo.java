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
     * 相亲
     */
    public void findLove() {
        System.out.println("你要找啥对象？");
        person.findLove();
        System.out.println("没有，自己new一个去");
    }

}
