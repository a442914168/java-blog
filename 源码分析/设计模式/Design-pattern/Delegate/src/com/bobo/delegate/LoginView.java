package com.bobo.delegate;

/**
 * @program: Delegate
 * @description:
 * @author: bobobo
 * @create: 2018-07-16 06:25
 **/
public class LoginView implements View {

    @Override
    public void draw(String viewType) {
        System.out.println("»­ " + viewType + "Ò³Ãæ");
    }
}
