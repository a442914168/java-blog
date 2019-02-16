package com.bobo.observer.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: Observer
 * @description: 事件的注册和监听
 * @author: bobobo
 * @create: 2018-07-18 21:44
 **/
public class EventListenter {

    //储存注册的事件
    protected Map<Enum, Event> events = new HashMap<Enum, Event>();

    /**
     * 注册监听
     *
     * @param eventType 事件类型枚举
     * @param target    通知目标类
     * @param callback  回调方法
     */
    public void addListenter(Enum eventType, Object target, Method callback) {
        events.put(eventType, new Event(target, callback));
    }

    /**
     * 反射调用回调方法
     *
     * @param event 事件类
     */
    private void tigger(Event event) {

        try {
            event.getCallback().invoke(event.getTarget(), event);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    /**
     * 触发事件
     *
     * @param call 事件枚举类型
     */
    protected void tigger(Enum call) {

        //没有添加监听，直接略过
        if (!this.events.containsKey(call)) {
            return;
        }

        //根据枚举类型，获取Event
        Event event = this.events.get(call);
        //设置Event的触发类型
        event.setTrigger(call.toString());
        //设置触发的来源
        event.setSource(this);
        //设置触发的时间
        event.setTime(System.currentTimeMillis());
        //反射调用回调方法
        tigger(event);

    }

}
