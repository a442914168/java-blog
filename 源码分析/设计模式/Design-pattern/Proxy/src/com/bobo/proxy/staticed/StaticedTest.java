package com.bobo.proxy.staticed;

import com.bobo.proxy.common.Zhangsan;

/**
 * @program: Proxy
 * @description: ��̬�������
 * @author: bobobo
 * @create: 2018-07-10 20:55
 **/
public class StaticedTest {

    public static void main(String[] args) {
        //��̬��������Ҫд��ȷ��֪�ķ���
        //��ý�� ���ܰ�������
        Meipo meipo = new Meipo(new Zhangsan());
        meipo.findLove();
    }

}
