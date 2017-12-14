package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ljh on 2017/8/1.
 */

/**
 * 普通广播
 */
public class BroadcastReceiver1 extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
        //接受广播信息
        String string = intent.getStringExtra("msg");
        Log.i("tag",string);

    }
}
