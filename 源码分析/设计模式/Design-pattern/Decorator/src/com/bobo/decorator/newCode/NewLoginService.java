package com.bobo.decorator.newCode;

import com.bobo.decorator.oldCode.OldLoginService;

/**
 * @program: Decorator
 * @description:
 * @author: bobobo
 * @create: 2018-07-17 10:47
 **/
public interface NewLoginService extends OldLoginService {

    /**
     * QQµÇÂ¼
     * @param openId
     */
    void login4QQ(String openId);

}
