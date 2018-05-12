package com.example.instance;

/**
 * Created by ljh on 2018/4/21.
 * 饿汉式
 */

public class HungryInstance {
    private static final HungryInstance instance = new HungryInstance();

    private HungryInstance(){}

    public static HungryInstance getInstance(){
        return instance;
    }
}
