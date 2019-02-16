package com.bobo.decorator.oldCode;

/**
 * @program: Decorator
 * @description:
 * @author: bobobo
 * @create: 2018-07-17 10:43
 **/
public class LoginService implements OldLoginService {

    @Override
    public void login(String userName, String pwd) {
        System.out.println("LoginService µÇÂ¼~~");
    }
}
