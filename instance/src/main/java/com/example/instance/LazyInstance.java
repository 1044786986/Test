package com.example.instance;

/**
 * Created by ljh on 2018/4/21.
 * 懒汉式
 */

public class LazyInstance {
    private static LazyInstance lazyInstance;
    private LazyInstance(){}

    /**
     * 在getInstance方法上同步
     */
//    public static synchronized LazyInstance getInstance(){
//        if(lazyInstance == null){
//            return new LazyInstance();
//        }
//        return lazyInstance;
//    }

    /**
     * 双重检查锁定
     * @return
     */
//    public static LazyInstance getInstance(){
//        if(lazyInstance == null){
//            synchronized(LazyInstance.class){
//                return new LazyInstance();
//            }
//        }
//        return lazyInstance;
//    }

    /**
     * 静态内部类
     */
    public static class LazyHolder{
        private static final LazyInstance lazyInstance = new LazyInstance();
    }
    public static final LazyInstance getInstance(){
        return LazyHolder.lazyInstance;
    }
}
