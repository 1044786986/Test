package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ljh on 2017/8/2.
 */

public class BindService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("tag","Service-onCreate()");
    }
    public class MyBindService extends Binder{
        public BindService getService(){
            return BindService.this;
        }

    }

    public IBinder onBind(Intent intent) {
        Log.i("tag","Service-onBind()");
        MyBindService myBindService = new MyBindService();
        return  myBindService;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.i("tag","Service-unbindService");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tag","Service-onDestroy()");
    }

    public void play(){
        Log.i("tag","播放");
    }

    public void pause(){
        Log.i("tag","暂停");
    }

    public void pervious(){
        Log.i("tag","上一首");
    }

    public void next(){
        Log.i("tag","下一首");
    }

}
