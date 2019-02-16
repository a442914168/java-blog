package com.bobo.singleton.hungry;

/**
 * @program: Singleton
 * @description: ����ʽ����
 *
 *  ����������ص�ʱ���������ʼ����
 *
 *  �ŵ㣺
 *      1��û�м��κε�����ִ��Ч�ʱȽϸߡ�
 *      2�����Ե��̰߳�ȫ����Ϊ�̻߳�û���ֵ�ʱ�򣬾�ʵ�����ˣ������ڷ��ʰ�ȫ���⡣
 *
 *  ȱ�㣺
 *      ����ص�ʱ��ͳ�ʼ�����������û��ǲ��á�
 *
 * @author: bobobo
 * @create: 2018-07-06 10:07
 **/
public class Hungry {

    private Hungry() {
        System.out.println("Hungry ��ʼ��~~");
    }

    //1��static
    //  ��̬���������еĶ������������ڴ���ֻ��һ�������������ҽ���������μ���ʱ�ᱻ��ʼ����
    //
    //2��final
    //  һ���㽫����������final���㽫���ܸı���������ˣ�������������룬�������ͼ�������ٴγ�ʼ���Ļ����������ᱨ�������
    //
    private static final Hungry hungry = new Hungry();

    public static Hungry getInstance() {
        return hungry;
    }

}
