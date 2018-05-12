package com.example.threadpoolexecutor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ljh on 2018/4/20.
 */

public class SleepAndWait extends AppCompatActivity{
    private Lock mLock = new ReentrantLock();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                sleep();
            }
        });

        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                sleep();
            }
        });
    }

    private void sleep(){
        mLock.lock();
//        synchronized(this){
            try {
                Log.d("aaa","sleep() = " + System.currentTimeMillis());
                Thread.sleep(2000);
                Log.d("aaa","sleep().start = " + System.currentTimeMillis());
//                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d("aaa","sleep().e = " + e);
            }finally {
                mLock.unlock();
            }
//        }
    }

    private void mWait(){
        mLock.lock();
//        synchronized(this){
            try {
                Log.d("aaa","wait() = " + System.currentTimeMillis());
                wait();
                Log.d("aaa","mWait().start = " + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//    }
}
