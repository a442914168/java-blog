package com.bobo.template;

/**
 * @program: Template
 * @description:
 * @author: bobobo
 * @create: 2018-07-15 20:31
 **/
public class Drinks {

    public void make(DrinksType type) {
        System.out.println("1.装水");
        System.out.println("2.烧水");
        System.out.println("3.加工");
        System.out.println(type.push());
        System.out.println("4.完成~");
    }

}
