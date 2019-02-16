package com.bobo.proxy.custom;

import com.bobo.proxy.common.Person;
import com.bobo.proxy.common.Zhangsan;

/**
 * @program: Proxy
 * @description:
 * @author: bobobo
 * @create: 2018-07-11 14:08
 **/
public class CustomTest {

    public static void main(String[] args) {
        try {
            Person person = (Person)new CustomMeipo().getInstance(new Zhangsan());
            person.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
