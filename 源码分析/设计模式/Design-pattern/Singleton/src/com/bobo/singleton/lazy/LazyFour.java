package com.bobo.singleton.lazy;

/**
 * @program: Singleton
 * @description: ����ʽ������
 *
 *  �ص㣺
 *      ���ⲿ�౻���õ�ʱ���ڲ���Żᱻ����
 *      �ڲ���һ����Ҫ�ڵ���֮ǰ��ʼ��
 *      ����ı������̰߳�ȫ����
 *      ������ʽ��˶���ʽ���ڴ��˷ѣ�Ҳ���synchronized��������
 *      ����˵����ţ�Ƶ�д����
 *
 * @author: bobobo
 * @create: 2018-07-07 07:26
 **/
public class LazyFour {

    //��־�Ƿ��ʼ������ֹ�����ظ���ʼ��
    private static boolean initialized = false;

    //Ĭ��ʹ��LazyThree��ʱ�򣬻��ȳ�ʼ���ڲ���
    //���ûʹ�õĻ����ڲ����ǲ����ص�
    private LazyFour() {
        synchronized (LazyFour.class) {
            if (initialized == false) {
                initialized = true;
                System.out.println("LazyFour ��ʼ��~~");
            } else {
                throw new RuntimeException("LazyFour �����Ѵ���~");
            }
        }
    }

    //ÿһ���ؼ��ֶ����Ƕ����
    //static ��Ϊ��ʹ�����Ŀռ乲��
    //final ��֤����������ᱻ��д������
    public static final LazyFour getInstance() {
        //�ڷ��ؽ����ǰ��һ�����ȼ����ڲ���
        return LazyHolder.LAZY;
    }

    //Ĭ�ϲ�����,���ʵ�ʱ��ż���
    private static class LazyHolder {
        private static final LazyFour LAZY = new LazyFour();
    }

}
