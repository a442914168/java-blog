package com.bobo.singleton.test;

import com.bobo.singleton.seriable.Seriable;

import java.io.*;

/**
 * @program: Singleton
 * @description: ���л���������
 * @author: bobobo
 * @create: 2018-07-07 11:26
 **/
public class SeriableTest {

    public static void main(String[] args) {
        Seriable s1 = null;
        Seriable s2 = Seriable.getInstance();

        FileOutputStream fos = null;
        try {
            //���л�s2
            fos = new FileOutputStream("Seriable.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();

            //�����л�
            FileInputStream fis = new FileInputStream("Seriable.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (Seriable)ois.readObject();
            ois.close();;

            //�Ա�s1 s2
            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s1 == s2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
