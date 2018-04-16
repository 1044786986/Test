package com.example.service_live;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by ljh on 2018/4/2.
 */

public class ScreenManager {
    private Context mContext;
    private WeakReference<Activity> mActivityWref;
    public static ScreenManager mScrennManager;

    public ScreenManager(Context context){
        mContext = context;
    }

    public static ScreenManager getInstance(Context context){
        if(mScrennManager == null){
            mScrennManager = new ScreenManager(context.getApplicationContext());
        }
        return mScrennManager;
    }

    public void startActivity(){
        LiveActivity.startLive();
    }

    public void finishActivity(){
        if(mActivityWref != null){
            Activity activity = mActivityWref.get();
            if(activity != null){
                activity.finish();
            }
        }
    }

    public void setmActivityWref(Activity activity) {
        mActivityWref = new WeakReference<Activity>(activity);
    }
}
