package com.bobo.observer.mouse;

import com.bobo.observer.core.EventListenter;

/**
 * @program: Observer
 * @description:
 * @author: bobobo
 * @create: 2018-07-18 22:07
 **/
public class Mouse extends EventListenter {

    public void click() {
        System.out.println("��갴��");
        //���ü�������
        this.tigger(MouseEventType.ON_CLICK);
    }

    public void doubleClick() {
        System.out.println("˫�����");
        //���ü�������
        this.tigger(MouseEventType.ON_DOUBLE_CLICK);
    }

}
