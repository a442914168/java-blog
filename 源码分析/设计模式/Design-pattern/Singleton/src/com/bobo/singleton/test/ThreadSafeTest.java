package com.bobo.singleton.test;

import java.util.concurrent.CountDownLatch;

/**
 * @program: Singleton
 * @description: 线程安全测试
 * @author: bobobo
 * @create: 2018-07-06 10:52
 **/
public class ThreadSafeTest {

    /**
     * 模拟一个线程并发请求
     * @param testClass 实现 SingetonTest 的类
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
                            // 阻塞,count = 0 就会释放所有的共享锁
                            latch.await();
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        //必然会调用，可能会有很多线程同时去访问getInstance()
                        Object obj = testClass.getInstance();
                        System.out.println(System.currentTimeMillis() + ":" + obj);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start(); //每循环一次，就启动一个线程,具有一定的随机性

            //每次启动一个线程，count --
            latch.countDown();

        }
        long end = System.currentTimeMillis();
        System.out.println("总耗时：" + (end - start));
    }

}
