package com.example.threadpoolexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by ljh on 2018/4/1.
 */

public class FixThreadPool {
    public static ExecutorService newFixThreadPool(int num){
        return new ThreadPoolExecutor(num,num)
    }
}
