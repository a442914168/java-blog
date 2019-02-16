package com.bobo.singleton.seriable;

import java.io.Serializable;

/**
 * @program: Singleton
 * @description: ���л���ֹ�ƻ�����
 *
 *  ���л���
 *      �������״̬��Ϣת��Ϊ���Դ洢�������ʽ�Ĺ���
 *      �Ӷ�ת��һ��IO����д�뵽�����ط�(�����Ǵ��̡�����IO)
 *      �ڴ���״̬�����ñ���������
 *
 *  �����л���
 *      ���Ѿ��־û����ֽ������ݣ�ת��ΪIO��
 *      ͨ��IO���Ķ�ȡ����������ȡ������ת��ΪJava����
 *      ��ת�������л����´�������new
 *
 *  ����취��
 *      ����readResolve()
 *
 * @author: bobobo
 * @create: 2018-07-07 11:15
 **/
public class Seriable implements Serializable {

    private Seriable() {
        System.out.println("Seriable ��ʼ��~~");
    }

    public final static Seriable instance = new Seriable();

    public static Seriable getInstance() {
        return instance;
    }

    //��JVM���ڴ��з����л���"��װ"һ���¶���ʱ��
    //�ͻ��Զ��������readResolve() ����������ָ���õĶ����ˣ���������Ҳ�͵õ��˱�֤��
    private Object readResolve() {
        return instance;
    }
}
