package com.example.threadpoolexecutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button,btSend,btAdd,btRemove;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            for(int i=0;i<1000000000;i++);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        btAdd = findViewById(R.id.btAdd);
        btSend = findViewById(R.id.btSend);
        btRemove = findViewById(R.id.btRemove);
        button.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        btSend.setOnClickListener(this);
        btRemove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Log.d("aaa","PoolSize = " + ThreadPoolManager.getInstance().getPoolSize());
                Log.d("aaa","CorePoolSize = " + ThreadPoolManager.getInstance().getCorePoolSize());
                Log.d("aaa","queue.size = " + ThreadPoolManager.getInstance().getQueue());
                Log.d("aaa","ActiveCount = " + ThreadPoolManager.getInstance().getActiveCount());
                Log.d("aaa","CompletedTaskCount = " +
                        ""+ThreadPoolManager.getInstance().getCompletedTaskCount());
                Log.d("aaa","LargestPoolSize = " + ThreadPoolManager.getInstance().getLargestPoolSize());
                Log.d("aaa","TaskCount = " + ThreadPoolManager.getInstance().getTaskCount());
                Log.d("aaa","-----------");
                break;
            case R.id.btAdd:
//                ThreadPoolManager.getInstance().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        for(int i=0;i<1000000000;i++);
//                    }
//                });
                ThreadPoolManager.getInstance().execute(runnable);
                break;
            case R.id.btRemove:
                ThreadPoolManager.getInstance().remove(runnable);
                break;
            case R.id.btSend:
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                finish();
                break;
        }
    }
}
