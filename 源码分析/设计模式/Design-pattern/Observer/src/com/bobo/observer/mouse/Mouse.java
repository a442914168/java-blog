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
        System.out.println("鼠标按下");
        //调用监听方法
        this.tigger(MouseEventType.ON_CLICK);
    }

    public void doubleClick() {
        System.out.println("双击鼠标");
        //调用监听方法
        this.tigger(MouseEventType.ON_DOUBLE_CLICK);
    }

}
