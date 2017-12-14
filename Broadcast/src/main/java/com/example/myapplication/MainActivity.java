package com.example.myapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button1;
    private Button button2;
    private Button button3;
    private YiBuBroadcastReceiver yiBuBroadcastReceiver;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                Intent intent = new Intent();
                intent.putExtra("msg","这是一条普通广播");
                intent.setAction("CommonBroadcast");
                sendBroadcast(intent);
                Toast.makeText(MainActivity.this, "已发送一条普通广播", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Intent intent1 = new Intent();
                intent1.putExtra("msg","这是一条有序广播");
                intent1.setAction("OrderedBroadcast");
                sendOrderedBroadcast(intent1,null);
                Toast.makeText(MainActivity.this, "已发送一条有序广播", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                /**
                 * 先发送后注册
                 * 使用动态注册
                 */
                Intent intent2 = new Intent();
                intent2.putExtra("msg","这是一条异步广播");
                intent2.setAction("BC_Three");
                sendStickyBroadcast(intent2);
                Toast.makeText(MainActivity.this, "已发送一条异步广播", Toast.LENGTH_SHORT).show();

                IntentFilter intentFilter = new IntentFilter("BC_Three");
                yiBuBroadcastReceiver = new YiBuBroadcastReceiver();
                registerReceiver(yiBuBroadcastReceiver,intentFilter);

                break;
        }
    }
}
