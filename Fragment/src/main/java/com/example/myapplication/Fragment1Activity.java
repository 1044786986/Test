package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
/**
 * Created by ljh on 2017/7/21.
 */

public class Fragment1Activity extends FragmentActivity implements FragmentListView.titleSelectInterface{
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment1);
    }
    @Override
    public void onTitleSelect(String Title) {
       android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
       FragmentText fragmentText = (FragmentText) fm.findFragmentById(R.id.tv);
        fragmentText.setText(Title);
    }
}
