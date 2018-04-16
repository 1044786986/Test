package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ljh on 2018/4/16.
 */

public class ThirdActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        EventBus.getDefault().post(new MessageEvent("123"));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("aaa","ThirdActivity.onDestroy()");
    }
}
