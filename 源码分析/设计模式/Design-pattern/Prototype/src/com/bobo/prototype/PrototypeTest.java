package com.bobo.prototype;

import sun.security.provider.Sun;

/**
 * @program: Prototype
 * @description: 原型模式测试
 * @author: bobobo
 * @create: 2018-07-09 07:06
 **/
public class PrototypeTest {

    public static void main(String[] args) {
        testDeepClone();
//        testClone();
    }

    /**
     * 使用自带的clone();方法
     */
    public static void testClone() {
        try {
            SunWuKongClone sunWuKongClone = new SunWuKongClone();
            SunWuKongClone sunWuKongClone2 = (SunWuKongClone) sunWuKongClone.clone();

            //让sunWuKongClone2 身高体重改变一下
            sunWuKongClone2.setHeight(220);
            sunWuKongClone2.setWeight(80);

            //让sunWuKongClone2 的金箍棒变大。
            sunWuKongClone2.getJinGuBang().big();

            //打印两只猴子的信息
            System.out.println("孙悟空\t 高："+ sunWuKongClone.getHeight() +
                    "\t重："+sunWuKongClone.getWeight()+
                    sunWuKongClone.getJinGuBang().toString());
            System.out.println("孙悟空\t 高："+ sunWuKongClone2.getHeight() +
                    "\t重："+sunWuKongClone2.getWeight()+
                    sunWuKongClone2.getJinGuBang().toString());

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static void testDeepClone() {
        try {
            SunWuKongDeepClone sunWuKongDeepClone = new SunWuKongDeepClone();
//            SunWuKongDeepClone sunWuKongDeepClone2 = (SunWuKongDeepClone) sunWuKongDeepClone.clone();
            SunWuKongDeepClone sunWuKongDeepClone2 = sunWuKongDeepClone.copy();

            //让sunWuKongClone2 身高体重改变一下
            sunWuKongDeepClone2.setHeight(220);
            sunWuKongDeepClone2.setWeight(80);

            //让sunWuKongClone2 的金箍棒变大。
            sunWuKongDeepClone2.getJinGuBang().big();

            //打印两只猴子的信息
            System.out.println("孙悟空\t 高："+ sunWuKongDeepClone.getHeight() +
                    "\t重："+sunWuKongDeepClone.getWeight()+
                    sunWuKongDeepClone.getJinGuBang().toString());
            System.out.println("孙悟空\t 高："+ sunWuKongDeepClone2.getHeight() +
                    "\t重："+sunWuKongDeepClone2.getWeight()+
                    sunWuKongDeepClone2.getJinGuBang().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
