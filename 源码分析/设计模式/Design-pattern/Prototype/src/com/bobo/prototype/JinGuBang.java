package com.bobo.prototype;

import java.io.Serializable;

/**
 * @program: Prototype
 * @description: 金箍棒类
 * @author: bobobo
 * @create: 2018-07-09 17:06
 **/
public class JinGuBang implements Serializable {

    private float h = 100;
    private float d = 10;

    /**
     * 变大方法
     */
    public void big() {
        this.d *= 2;
        this.h *= 2;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "JinGuBang{" +
                "h=" + h +
                ", d=" + d +
                '}';
    }
}
