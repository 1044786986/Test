package com.example.java_reflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ljh on 2018/2/27.
 */

public class Main {

    public static void main(String[] args){
        System.out.println("开始运行");
        Main main = new Main();
        main.getAllConstructor();
        System.out.println("运行完毕");
    }

    public void getAllConstructor(){
        try {
            Class c = Class.forName("com.example.java_reflex.Student");
            /**
             * 所有公有构造方法
             */
            System.out.println("************所有公有构造方法********************");
            Constructor[] constructors = c.getConstructors();
            for (Constructor i:constructors){
                System.out.println(i);
            }
            System.out.println();

            /**
             * 所有的构造方法(包括：私有、受保护、默认、公有)
             */
            System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)********************");
            Constructor[] constructors1 = c.getDeclaredConstructors();
            for(Constructor i:constructors1){
                System.out.println(i);
            }
            System.out.println();

            /**
             * 调用私有方法
             */
            System.out.println("************调用私有方法********************");
            Constructor con = c.getDeclaredConstructor(char.class);
            con.setAccessible(true);    //暴力访问(忽略掉访问修饰符)
            Object object = con.newInstance('男');


            /**
             * 获取所有公有字段
             */
            System.out.println("************获取所有公有的字段********************");
            Field[] fields = c.getFields();
            for(Field f:fields){
                System.out.println(f);
            }
            System.out.println();

            /**
             * 获取所有的字段(包括私有、受保护、默认的)
             */
            System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
            fields = c.getDeclaredFields();
            for (Field f:fields){
                System.out.println(f);
            }
            System.out.println();

            /**
             * 获取公有字段**并调用
             */
            System.out.println("*************获取公有字段**并调用***********************************");
            Field field = c.getField("age");
            System.out.println(field);
//            Field field1 = c.getDeclaredField("name");
//            field1.setAccessible(true);
            Object object1 = c.getConstructor().newInstance();
            field.set(object1,21);
//            field1.set(object,"ljh");
            Student student = (Student) object1;
            System.out.println("age = " + student.age);


            /**
             * 获取所有的方法，包括私有的
             */
            System.out.println("***************获取所有的方法，包括私有的*******************");
            Method[] methods = c.getDeclaredMethods();
            for (Method m:methods){
                System.out.println(m);
            }
            System.out.println();

            /**
             * 调用其中的方法
             */
            System.out.println("***************调用其中的方法*******************");
            Method method = c.getMethod("setName", String.class);
            Method method1 = c.getMethod("getName");
            Object object2 = c.getConstructor().newInstance();
            method.invoke(object2,"ljh");
            Object name = method1.invoke(object2);
            System.out.println(name);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
