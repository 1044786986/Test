package com.example.java_reflex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by ljh on 2018/2/27.
 */

public class Configuration_file {

    public static void main(String[] args){
        try {
            Class c = Class.forName(getValue("className")); //通过反射获取Class对象
            Method method = c.getMethod(getValue("methodName"));
            method.invoke(c.getConstructor().newInstance());

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
        }
    }

    private static String getValue(String key){
        try {
            Properties properties = new Properties();
            FileReader fileReader = new FileReader("D:\\androidfile\\AnswerAssistant\\txt\\Configuration_txt.txt");
            properties.load(fileReader);
            fileReader.close();
            return properties.getProperty(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
