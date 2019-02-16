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
        System.out.println("验证 数据库是否已经注册");
        System.out.println("注册 或 获取绑定的账号密码");
        loginService.login("userName","pwd");
        System.out.println("登录成功~");
    }

    @Override
    public void login(String userName, String pwd) {
        this.loginService.login(userName, pwd);
    }
}
