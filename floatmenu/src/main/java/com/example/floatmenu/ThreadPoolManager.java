package com.example.floatmenu;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ljh on 2018/2/24.
 */

public class ThreadPoolManager {
    public static ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    private int corePoolSize;
    private int maxPoolSize;
    private long keepTime = 1;
    private TimeUnit timeUnit = TimeUnit.HOURS;
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolManager(){
        corePoolSize = Runtime.getRuntime().availableProcessors()*2+1;
        maxPoolSize = corePoolSize;
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepTime,timeUnit,
                new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadPoolManager getThreadPoolManager(){
        return threadPoolManager;
    }

    public void execute(Runnable runnable){
        if(runnable == null){
            return;
        }
        threadPoolExecutor.execute(runnable);
    }

    public void remove(Runnable runnable){
        if(runnable == null){
            return;
        }
        threadPoolExecutor.remove(runnable);
    }
}
