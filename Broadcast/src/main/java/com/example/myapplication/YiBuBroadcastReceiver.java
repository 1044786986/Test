package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ljh on 2017/8/1.
 */

public class YiBuBroadcastReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
        String string = intent.getStringExtra("msg");
        Log.i("YiBuBroadcastReceiver",string);
    }
}
