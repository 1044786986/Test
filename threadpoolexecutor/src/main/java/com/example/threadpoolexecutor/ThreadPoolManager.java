package com.example.threadpoolexecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ljh on 2018/4/1.
 */

public class ThreadPoolManager {
    public static final ThreadPoolManager cachePoolThread = new ThreadPoolManager();
    private ThreadPoolExecutor mThreadPoolExecutor;
    private int maxPoolSize;
    private int corePoolSize;
    private int time = 5;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    /**
     *
     */
    public ThreadPoolManager(){
        corePoolSize = Runtime.getRuntime().availableProcessors()*2+1;
//        Log.d("aaa","corePoolSize = " + corePoolSize);
        maxPoolSize = corePoolSize;
        mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maxPoolSize,time, timeUnit
        ,new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
//        mThreadPoolExecutor.allowsCoreThreadTimeOut();
    }

    public static ThreadPoolManager getInstance(){
        return cachePoolThread;
    }

    public int getPoolSize(){
        return mThreadPoolExecutor.getPoolSize();
    }

    public int getCorePoolSize(){
        return mThreadPoolExecutor.getCorePoolSize();
    }

    public int getQueue(){
        return mThreadPoolExecutor.getQueue().size();
    }

    public int  getActiveCount(){
        return mThreadPoolExecutor.getActiveCount();
    }

    public int getCompletedTaskCount(){
        return (int) mThreadPoolExecutor.getCompletedTaskCount();
    }

    public int getLargestPoolSize(){
        return mThreadPoolExecutor.getLargestPoolSize();
    }

    public int getMaximumPoolSize(){
        return mThreadPoolExecutor.getMaximumPoolSize();
    }

    public int getTaskCount(){
        return (int) mThreadPoolExecutor.getTaskCount();
    }


    public void execute(Runnable runnable){
        if(runnable != null){
            mThreadPoolExecutor.execute(runnable);
        }
    }

    public void remove(Runnable runnable){
        if(runnable != null){
            mThreadPoolExecutor.remove(runnable);
        }
    }
}
