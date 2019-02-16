package com.bobo.prototype;

import java.io.*;
import java.util.Date;

/**
 * @program: Prototype
 * @description: 孙悟空，深拷贝
 * @author: bobobo
 * @create: 2018-07-09 17:28
 **/
public class SunWuKongDeepClone extends Monkey implements Cloneable, Serializable {

    private JinGuBang jinGuBang;

    /**
     * 调用clone()，不会调用构造方法
     */
    public SunWuKongDeepClone() {
        this.setJinGuBang(new JinGuBang());
        System.out.println("SunWuKongDeepClone 初始化~");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.deepClone();
    }

    /**
     * 采用序列化形式，可以节省时间，免得一个个字段赋值
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
     * 封装一个复制方法，new一个新类，一项项复制
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
