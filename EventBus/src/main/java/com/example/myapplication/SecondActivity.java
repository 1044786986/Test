package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ljh on 2018/2/23.
 */

public class SecondActivity extends AppCompatActivity{
    private Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secound);
        EventBus.getDefault().register(this);
        btSend = findViewById(R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EventBus.getDefault().post(new MessageEvent("哈哈哈哈"));
//                finish();
//                EventBus.getDefault().register(this);
                startActivity(new Intent(SecondActivity.this,ThirdActivity.class));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        btSend.setText(messageEvent.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        Log.i("aaa","SecondActivity.onDestroy()");
    }
}
