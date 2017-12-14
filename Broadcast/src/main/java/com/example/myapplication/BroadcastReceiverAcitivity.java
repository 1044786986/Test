package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by ljh on 2017/8/1.
 */

/**
 * 接受上一条广播和处理的数据
 */
public class BroadcastReceiverAcitivity extends Activity {
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcastreceiver);

        this.textView = (TextView) findViewById(R.id.tv);


    }

}
