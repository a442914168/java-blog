package com.bobo.prototype;

import sun.security.provider.Sun;

/**
 * @program: Prototype
 * @description: ԭ��ģʽ����
 * @author: bobobo
 * @create: 2018-07-09 07:06
 **/
public class PrototypeTest {

    public static void main(String[] args) {
        testDeepClone();
//        testClone();
    }

    /**
     * ʹ���Դ���clone();����
     */
    public static void testClone() {
        try {
            SunWuKongClone sunWuKongClone = new SunWuKongClone();
            SunWuKongClone sunWuKongClone2 = (SunWuKongClone) sunWuKongClone.clone();

            //��sunWuKongClone2 ������ظı�һ��
            sunWuKongClone2.setHeight(220);
            sunWuKongClone2.setWeight(80);

            //��sunWuKongClone2 �Ľ𹿰����
            sunWuKongClone2.getJinGuBang().big();

            //��ӡ��ֻ���ӵ���Ϣ
            System.out.println("�����\t �ߣ�"+ sunWuKongClone.getHeight() +
                    "\t�أ�"+sunWuKongClone.getWeight()+
                    sunWuKongClone.getJinGuBang().toString());
            System.out.println("�����\t �ߣ�"+ sunWuKongClone2.getHeight() +
                    "\t�أ�"+sunWuKongClone2.getWeight()+
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

            //��sunWuKongClone2 ������ظı�һ��
            sunWuKongDeepClone2.setHeight(220);
            sunWuKongDeepClone2.setWeight(80);

            //��sunWuKongClone2 �Ľ𹿰����
            sunWuKongDeepClone2.getJinGuBang().big();

            //��ӡ��ֻ���ӵ���Ϣ
            System.out.println("�����\t �ߣ�"+ sunWuKongDeepClone.getHeight() +
                    "\t�أ�"+sunWuKongDeepClone.getWeight()+
                    sunWuKongDeepClone.getJinGuBang().toString());
            System.out.println("�����\t �ߣ�"+ sunWuKongDeepClone2.getHeight() +
                    "\t�أ�"+sunWuKongDeepClone2.getWeight()+
                    sunWuKongDeepClone2.getJinGuBang().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
