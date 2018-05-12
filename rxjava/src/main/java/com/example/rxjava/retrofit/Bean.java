package com.example.rxjava.retrofit;

import android.util.Log;

/**
 * Created by ljh on 2018/4/17.
 */

public class Bean {
    private int status;

    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.d("aaa",status+"");
        Log.d("aaa",content.from+"");
        Log.d("aaa",content.to+"");
        Log.d("aaa",content.vendor+"");
        Log.d("aaa",content.out+"");
        Log.d("aaa",content.errNo+"");
    }
}
