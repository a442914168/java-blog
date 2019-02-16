package com.bobo.singleton.test;

import java.util.concurrent.CountDownLatch;

/**
 * @program: Singleton
 * @description: �̰߳�ȫ����
 * @author: bobobo
 * @create: 2018-07-06 10:52
 **/
public class ThreadSafeTest {

    /**
     * ģ��һ���̲߳�������
     * @param testClass ʵ�� SingetonTest ����
     */
    public void start(SingetonTest testClass) {

        int count = 200;

        CountDownLatch latch = new CountDownLatch(count);

        long start = System.currentTimeMillis();
        for (int i = 0; i < count;i ++) {
            new Thread(){
                @Override
                public void run() {
                    try{

                        try {
                            // ����,count = 0 �ͻ��ͷ����еĹ�����
                            latch.await();
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        //��Ȼ����ã����ܻ��кܶ��߳�ͬʱȥ����getInstance()
                        Object obj = testClass.getInstance();
                        System.out.println(System.currentTimeMillis() + ":" + obj);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start(); //ÿѭ��һ�Σ�������һ���߳�,����һ���������

            //ÿ������һ���̣߳�count --
            latch.countDown();

        }
        long end = System.currentTimeMillis();
        System.out.println("�ܺ�ʱ��" + (end - start));
    }

}
