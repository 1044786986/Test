package com.example.threadpoolexecutor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by ljh on 2018/4/18.
 */

public class SecondActivity extends AppCompatActivity{
    private Button btCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btCount = findViewById(R.id.btCount);
        btCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aaa","ActiveCount = " + ThreadPoolManager.getInstance().getActiveCount());
                Log.d("aaa","CompletedTaskCount = " +
                        ""+ThreadPoolManager.getInstance().getCompletedTaskCount());
                Log.d("aaa","LargestPoolSize = " + ThreadPoolManager.getInstance().getLargestPoolSize());
                Log.d("aaa","MaximumPoolSize = " + ThreadPoolManager.getInstance().getMaximumPoolSize());
                Log.d("aaa","TaskCount = " + ThreadPoolManager.getInstance().getTaskCount());
                Log.d("aaa","------------");
            }
        });
    }
}
