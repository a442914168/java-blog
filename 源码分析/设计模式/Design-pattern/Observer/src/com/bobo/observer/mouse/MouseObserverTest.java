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

            //�ص���
            MouseEventCallback target = new MouseEventCallback();
            //�ص��࣬��Ӧ�ķ���
            Method callBackMethod = MouseEventCallback.class.getMethod("onClick", Event.class);

            //�����
            Mouse mouse = new Mouse();
            //��Ӽ����¼�
            mouse.addListenter(MouseEventType.ON_CLICK, target, callBackMethod);

            //���÷���
            mouse.click(); 

        } catch (NoSuchMethodException e) {

            e.printStackTrace();

        }

    }

}
