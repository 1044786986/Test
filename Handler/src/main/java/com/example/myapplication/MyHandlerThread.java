package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.HandlerThread;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by ljh on 2017/8/9.
 */

public class MyHandlerThread extends Activity implements View.OnClickListener{
    private Button btStart;
    private Button btStop;
    private Handler childThread;
    private ImageView imageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handlerthread);

        imageView = (ImageView) findViewById(R.id.imageView);
        btStart = (Button) findViewById(R.id.btStart);
        btStop = (Button) findViewById(R.id.btStop);
        btStart.setOnClickListener(this);
        btStop.setOnClickListener(this);

        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        childThread = new Handler(handlerThread.getLooper()){

            public void handleMessage(Message msg) {
                Message message = new Message();
                message.what = 1;
                Log.i("tag","ChildHandler");
                handler.sendMessageDelayed(message,1000);
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
        };


    }

    private Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            Message message = new Message();
            message.what = 1;
            Log.i("tag","mainHandler");
            childThread.sendMessageDelayed(message,1000);
        }
    };

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btStart:
                handler.sendEmptyMessage(1);
                Toast.makeText(MyHandlerThread.this,"开始",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btStop:
                handler.removeMessages(1);
                Toast.makeText(MyHandlerThread.this,"暂停",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
