package com.bobo.prototype;

import java.io.*;
import java.util.Date;

/**
 * @program: Prototype
 * @description: ����գ����
 * @author: bobobo
 * @create: 2018-07-09 17:28
 **/
public class SunWuKongDeepClone extends Monkey implements Cloneable, Serializable {

    private JinGuBang jinGuBang;

    /**
     * ����clone()��������ù��췽��
     */
    public SunWuKongDeepClone() {
        this.setJinGuBang(new JinGuBang());
        System.out.println("SunWuKongDeepClone ��ʼ��~");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.deepClone();
    }

    /**
     * �������л���ʽ�����Խ�ʡʱ�䣬���һ�����ֶθ�ֵ
     * @return
     */
    public Object deepClone() {
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            return ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ��װһ�����Ʒ�����newһ�����࣬һ�����
     * @return
     */
    public SunWuKongDeepClone copy() {
        SunWuKongDeepClone newInstance = new SunWuKongDeepClone();
        newInstance.setWeight(this.getWeight());
        newInstance.setHeight(this.getHeight());

        newInstance.jinGuBang = new JinGuBang();
        newInstance.jinGuBang.setH(this.jinGuBang.getH());
        newInstance.jinGuBang.setD(this.jinGuBang.getD());

        return newInstance;
    }

    public JinGuBang getJinGuBang() {
        return jinGuBang;
    }

    public void setJinGuBang(JinGuBang jinGuBang) {
        this.jinGuBang = jinGuBang;
    }
}
