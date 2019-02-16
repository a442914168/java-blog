package com.bobo.delegate;

/**
 * @program: Delegate
 * @description:
 * @author: bobobo
 * @create: 2018-07-16 06:24
 **/
public class Client {

    public static void main(String[] args) {

        ViewController viewController = new ViewController();
        viewController.push("Login");

    }

}
