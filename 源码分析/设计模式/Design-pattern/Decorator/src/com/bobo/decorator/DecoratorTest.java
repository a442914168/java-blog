package com.bobo.decorator;

import com.bobo.decorator.newCode.Login4ThridService;
import com.bobo.decorator.oldCode.LoginService;

/**
 * @program: Decorator
 * @description:
 * @author: bobobo
 * @create: 2018-07-17 10:50
 **/
public class DecoratorTest {

    public static void main(String[] args) {

        //原来的功能依旧对外开放，依旧保留
        //新的功能同样的也可以使用
        Login4ThridService login4ThridService = new Login4ThridService(new LoginService());

        System.out.println("=======login4ThridService.login=========");
        login4ThridService.login("zhangsan", "pwd11");

        System.out.println("");
        System.out.println("=======login4ThridService.login4Q=========");
        login4ThridService.login4QQ("aqq122112");
    }

}
