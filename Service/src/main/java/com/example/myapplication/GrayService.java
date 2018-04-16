package com.example.myapplication;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ljh on 2018/4/2.
 */

public class GrayService extends Service{
    private static final int GRAY_SERVICE_ID = 1001;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent1 = new Intent(this,MyGrayService.class);
        startService(intent1);
        startForeground(GRAY_SERVICE_ID,new Notification());
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyGrayService extends Service{
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID,new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
