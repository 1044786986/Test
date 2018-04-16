package com.example.myapplication;

/**
 * Created by ljh on 2018/2/23.
 */

public class MessageEvent{

    private String message;

    public MessageEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
