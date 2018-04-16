package com.example.service_live;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ljh on 2018/4/2.
 */

public class Broadcast_lock_screen extends BroadcastReceiver{
    public interface LockScreenListener{
        public void openScreen();
        public void lockScreen();
    }
    private LockScreenListener mLockScreenListener;

    Broadcast_lock_screen(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Intent.ACTION_SCREEN_OFF.equals(action)){
            mLockScreenListener.lockScreen();
        }else if(Intent.ACTION_SCREEN_ON.equals(action)){
            mLockScreenListener.openScreen();
        }
    }

    public void setLockScreenListener(LockScreenListener lockScreenListener){
        if(lockScreenListener != null){
            mLockScreenListener = lockScreenListener;
        }
    }
}
