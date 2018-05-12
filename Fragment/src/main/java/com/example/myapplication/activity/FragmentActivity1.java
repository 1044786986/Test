package com.example.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.fragment.Fragment1;

/**
 * Created by ljh on 2018/4/21.
 */

public class FragmentActivity1 extends FragmentActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityfragment1);
        Log.d("aaa","activity.onCreate()");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new Fragment1();
        fragmentTransaction.add(R.id.linearLayout,fragment,"fragment1");
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("aaa","activity.onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("aaa","activity.onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("aaa","activity.onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("aaa","activity.onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("aaa","activity.onDestroy()");
    }
}
