package com.bobo.observer.mouse;

import com.bobo.observer.core.Event;

/**
 * @program: Observer
 * @description:
 * @author: bobobo
 * @create: 2018-07-18 22:10
 **/
public class MouseEventCallback {

    public void onClick(Event event) {
        System.out.println("��������굥��: \n" + event);
    }

    public void onDoubleClick(Event event) {
        System.out.println("���������˫��: \n" + event);
    }

}
