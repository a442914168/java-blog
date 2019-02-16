package com.bobo.adapter.newCode;

import com.bobo.adapter.oldCode.LoginService;

/**
 * @program: Adapter
 * @description: 第三方登录
 * @author: bobobo
 * @create: 2018-07-16 17:42
 **/
public class Login4ThridService extends LoginService {

    /**
     * 不改变原有的login代码
     *
     * @param openId qq登录后返回的
     */
    public void login4QQ(String openId) {
        System.out.println("验证 数据库是否已经注册");
        System.out.println("注册 或 获取绑定的账号密码");
        super.login("userName","pwd");
        System.out.println("登录成功~");
    }

}
