package com.example.service_live;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.i("aaa","onCreate");
        intent = new Intent(this,LiveService.class);
        startService(intent);
        startService(new Intent(this,MyJobService.class));
        startService(new Intent(this,NotificationListener.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("aaa","onDestroy");
    }
}
