package com.bobo.observer.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: Observer
 * @description: �¼���ע��ͼ���
 * @author: bobobo
 * @create: 2018-07-18 21:44
 **/
public class EventListenter {

    //����ע����¼�
    protected Map<Enum, Event> events = new HashMap<Enum, Event>();

    /**
     * ע�����
     *
     * @param eventType �¼�����ö��
     * @param target    ֪ͨĿ����
     * @param callback  �ص�����
     */
    public void addListenter(Enum eventType, Object target, Method callback) {
        events.put(eventType, new Event(target, callback));
    }

    /**
     * ������ûص�����
     *
     * @param event �¼���
     */
    private void tigger(Event event) {

        try {
            event.getCallback().invoke(event.getTarget(), event);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    /**
     * �����¼�
     *
     * @param call �¼�ö������
     */
    protected void tigger(Enum call) {

        //û����Ӽ�����ֱ���Թ�
        if (!this.events.containsKey(call)) {
            return;
        }

        //����ö�����ͣ���ȡEvent
        Event event = this.events.get(call);
        //����Event�Ĵ�������
        event.setTrigger(call.toString());
        //���ô�������Դ
        event.setSource(this);
        //���ô�����ʱ��
        event.setTime(System.currentTimeMillis());
        //������ûص�����
        tigger(event);

    }

}
