package com.bobo.decorator.newCode;

import com.bobo.decorator.oldCode.LoginService;

/**
 * @program: Decorator
 * @description:
 * @author: bobobo
 * @create: 2018-07-17 10:43
 **/
public class Login4ThridService implements NewLoginService {

    LoginService loginService;

    public Login4ThridService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void login4QQ(String openId) {
        System.out.println("��֤ ���ݿ��Ƿ��Ѿ�ע��");
        System.out.println("ע�� �� ��ȡ�󶨵��˺�����");
        loginService.login("userName","pwd");
        System.out.println("��¼�ɹ�~");
    }

    @Override
    public void login(String userName, String pwd) {
        this.loginService.login(userName, pwd);
    }
}
