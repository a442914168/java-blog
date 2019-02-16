package com.bobo.observer.core;

import java.lang.reflect.Method;

/**
 * @program: Observer
 * @description:
 * @author: bobobo
 * @create: 2018-07-18 21:35
 **/
public class Event {

    //�¼�Դ�����������ࣩ
    private Object source;
    //֪ͨĿ����
    private Object target;
    //�ص�����
    private Method callback;
    //�����¼�����
    private String trigger;
    //����ʱ��
    private Long time;

    public Event(Object target, Method callback) {
        this.target = target;
        this.callback = callback;
    }

    @Override
    public String toString() {
        return "Event{" +
                "\n\tsource=" + source + ",\n" +
                "\ttarget=" + target + ",\n" +
                "\tcallback=" + callback + ",\n" +
                "\ttrigger='" + trigger + '\'' + "\n" +
                '}';
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getTarget() {
        return target;
    }

    public Method getCallback() {
        return callback;
    }

    public void setCallback(Method callback) {
        this.callback = callback;
    }

    public String getTrigger() {
        return trigger;
    }

    public Long getTime() {
        return time;
    }

    public Event setTarget(Object target) {
        this.target = target;
        return this;
    }

    public Event setTime(Long time) {
        this.time = time;
        return this;
    }

    public Event setTrigger(String trigger) {
        this.trigger = trigger;
        return this;
    }
}
