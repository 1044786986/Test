package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ljh on 2017/8/1.
 */

/**
 * 有序广播
 */
public class OrderedBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        //接收广播信息
        String string = intent.getStringExtra("msg");
        Log.i("OrderedBroadcast1",string);
        //处理数据,发送数据
        Bundle bundle = new Bundle();
        bundle.putString("text","这是第一条广播发送的数据");
        setResultExtras(bundle);

        /**
         * 在另外一个程序接收数据
         * Bundle bundle = getResultExtras(true);
         * String string = bundle.getString("text")
         * Log.i
         */
    }
}
