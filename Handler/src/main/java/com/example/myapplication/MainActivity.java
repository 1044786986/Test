package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText("Hello World");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textView = (TextView) findViewById(R.id.tv);
        /**
         * 四种方法更新UI
         * handler.post(new Runnable)
         * handler.sendEmptyMessage(what)
         * runOnUiThread(new Runnable)
         * textview.post(new Runnable)
         */
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    handler.post(new Runnable() {
                        public void run() {
                            textView.setText("Hello World");
                        }
                    });
                    //handler1();
                    //handler2();
                    //updateUI();
                    //viewUI();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

            }
    //第一种方法，handler.post
    public void handler1(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("Hello World");
            }
        });
    }
    //第二种方法,handler.sendEmptyMessage
    public void handler2(){
        handler.sendEmptyMessage(1);
    }
    //第三种方法,runOnUiThread
    public void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("Hello World");
            }
        });
    }
    //第四种方法,textview.post
    public void viewUI(){
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("Hello World");
            }
        });
    }

        }