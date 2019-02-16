package com.bobo.observer.mouse;

import com.bobo.observer.core.Event;

import java.lang.reflect.Method;

/**
 * @program: Observer
 * @description:
 * @author: bobobo
 * @create: 2018-07-18 22:12
 **/
public class MouseObserverTest {

    public static void main(String[] args) {

        try {

            //回调类
            MouseEventCallback target = new MouseEventCallback();
            //回调类，对应的方法
            Method callBackMethod = MouseEventCallback.class.getMethod("onClick", Event.class);

            //鼠标类
            Mouse mouse = new Mouse();
            //添加监听事件
            mouse.addListenter(MouseEventType.ON_CLICK, target, callBackMethod);

            //调用方法
            mouse.click(); 

        } catch (NoSuchMethodException e) {

            e.printStackTrace();

        }

    }

}
