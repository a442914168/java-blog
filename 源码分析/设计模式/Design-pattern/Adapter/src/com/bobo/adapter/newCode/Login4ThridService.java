package com.bobo.adapter.newCode;

import com.bobo.adapter.oldCode.LoginService;

/**
 * @program: Adapter
 * @description: ��������¼
 * @author: bobobo
 * @create: 2018-07-16 17:42
 **/
public class Login4ThridService extends LoginService {

    /**
     * ���ı�ԭ�е�login����
     *
     * @param openId qq��¼�󷵻ص�
     */
    public void login4QQ(String openId) {
        System.out.println("��֤ ���ݿ��Ƿ��Ѿ�ע��");
        System.out.println("ע�� �� ��ȡ�󶨵��˺�����");
        super.login("userName","pwd");
        System.out.println("��¼�ɹ�~");
    }

}
