package com.example.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btGray;
    Intent intent;
    Intent intent1;
    BindService mybindService;
    ServiceConnection serviceConnection = new ServiceConnection() {
        /**
         * 当启动源与service成功连接后会自动调用这个方法
         * @param componentName
         * @param iBinder
         */
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mybindService = ((BindService.MyBindService)iBinder).getService();
            Log.i("tag","onServiceConnected");
        }

        /**
         * 当service服务崩溃或被强行杀死，或者与启动源连接意外丢失会自动调用这个方法
         * @param componentName
         */
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("tag","onServiceDisconnected");
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btGray = (Button) findViewById(R.id.btGray);
        btGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this,GrayService.class));
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btStart:
                intent = new Intent(MainActivity.this,ReceptionService.class);
//                startService(intent);
                startService(new Intent(MainActivity.this,StartService.class));
                break;
            case R.id.btStop:
                stopService(intent);
                break;
            case R.id.btBind:
                intent1 = new Intent(MainActivity.this,BindService.class);
                bindService(intent1,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.btUnBind:
                unbindService(serviceConnection);
                break;
            case R.id.btStar:
                mybindService.play();
                break;
            case R.id.btPause:
                mybindService.pause();
                break;
            case R.id.btpervious:
                mybindService.pervious();
                break;
            case R.id.btNext:
                mybindService.next();
                break;
        }
    }
}
