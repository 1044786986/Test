package com.example.myapplication;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ljh on 2018/4/2.
 */

public class ReceptionService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("aaa","onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("aaa","Start-onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("aaa","onStartCommand");
        Intent intent1 = new Intent(this,MainActivity.class);
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("QQ音乐");
        builder.setContentText("你还要我怎样");
        builder.setPriority(Notification.PRIORITY_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(startId,builder.build());
        startForeground(startId,builder.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}
