package com.example.service_live;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ljh on 2018/4/2.
 */

public class LiveService extends Service{
    public static final int SERVICE_ID = 1001;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("aaa","LiveService.onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("aaa","LiveService.onStartCommand");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startForeground(SERVICE_ID,builder.build());
            startService(new Intent(this,LiveService2.class));

//            try {
//                Thread.sleep(10000);
//                Intent intent1 = new Intent(this,MainActivity.class);
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(intent1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        return START_STICKY;
    }
}
