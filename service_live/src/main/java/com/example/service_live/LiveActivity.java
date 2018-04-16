package com.example.service_live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ljh on 2018/4/2.
 */

public class LiveActivity extends AppCompatActivity{
    public static final String TAG = LiveActivity.class.getSimpleName();
    private Window mWindow;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        initView();
    }

    public static void startLive(){

    }

    private void initView(){
//        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        mLayoutParams = new WindowManager.LayoutParams();
//        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
//        mLayoutParams.width = 1;
//        mLayoutParams.height = 1;
//        mLayoutParams.x = 0;
//        mLayoutParams.y = 0;
        mWindow = getWindow();
        mLayoutParams = mWindow.getAttributes();
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.width = 1;
        mLayoutParams.height = 1;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
        mWindow.setAttributes(mLayoutParams);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("aaa","LiveActivity.onDestroy");
    }
}
