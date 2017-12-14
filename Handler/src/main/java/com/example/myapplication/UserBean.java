package com.example.myapplication;

/**
 * Created by ljh on 2017/8/8.
 */

public class UserBean {

    public String toString(){
        return "id=" + getId() + " " + "name=" + getName() + " " + "age=" + getAge();
    }
    private int id = 007;
    private String name = "珊珊";
    private int age = 20;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
