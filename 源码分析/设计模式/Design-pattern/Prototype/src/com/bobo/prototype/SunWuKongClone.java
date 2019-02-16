package com.bobo.prototype;

import java.util.Date;

/**
 * @program: Prototype
 * @description: ����գ�clone
 * @author: bobobo
 * @create: 2018-07-09 17:26
 **/
public class SunWuKongClone extends Monkey implements Cloneable {

    private JinGuBang jinGuBang;

    /**
     * ����clone()��������ù��췽��
     */
    public SunWuKongClone() {
        this.setJinGuBang(new JinGuBang());
        System.out.println("SunWuKongClone ��ʼ��~");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public JinGuBang getJinGuBang() {
        return jinGuBang;
    }

    public void setJinGuBang(JinGuBang jinGuBang) {
        this.jinGuBang = jinGuBang;
    }

    @Override
    public String toString() {
        return "SunWuKongClone{" +
                "jinGuBang=" + jinGuBang +
                '}';
    }
}
