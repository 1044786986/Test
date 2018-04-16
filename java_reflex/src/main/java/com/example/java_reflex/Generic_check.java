package com.example.java_reflex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2018/2/27.
 */

public class Generic_check {

    public static void main(String[]args){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        try {
            Class c = list.getClass();
            Method method = c.getMethod("add",Object.class);
            method.invoke(list,"梁杰濠");
            for (Object o:list){
                System.out.println(o);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
