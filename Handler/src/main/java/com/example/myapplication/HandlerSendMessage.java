package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by ljh on 2017/8/8.
 */

public class HandlerSendMessage extends Activity{
    private TextView textView;
    Message message = new Message();
    UserBean userBean = new UserBean();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmessage);

        this.textView = (TextView) findViewById(R.id.tv1);

        new Thread(){
            public void run(){
                try {
                    Thread.sleep(1000);
                    //message.arg1 = 100;
                    //message.arg2 = 200;
                    //message.obj = userBean.getId() + userBean.getName() + userBean.getAge();
                    Message message = handler.obtainMessage();
                    message.obj = userBean;
                   // handler.sendMessage(message);//发送message
                    message.sendToTarget();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //textView.setText(msg.arg1 + " " + msg.arg2);
            textView.setText(msg.obj +"");
        }
    };


}
