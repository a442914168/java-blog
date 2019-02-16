package com.bobo.prototype;

import java.util.Date;

/**
 * @program: Prototype
 * @description: ºï×ÓÀà
 * @author: bobobo
 * @create: 2018-07-09 17:03
 **/
public class Monkey {

    private int height;
    private int weight;

    public Monkey() {
        this.height = 110;
        this.weight = 40;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Monkey{" +
                "height=" + height +
                ", weight=" + weight +
                '}';
    }
}
