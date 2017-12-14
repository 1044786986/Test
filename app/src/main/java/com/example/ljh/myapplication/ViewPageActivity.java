package com.example.ljh.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2017/7/13.
 */

public class ViewPageActivity extends Activity {
    private ViewPager padapter;
    private PagerTabStrip tab;
    List<View> viewList;
    List<String>tabList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);

        viewList = new ArrayList<View>();
        tabList = new ArrayList<String>();
        //通过view对象去作为Viewpage的数据源
        View view1 = View.inflate(this,R.layout.view1,null);
        View view2 = View.inflate(this,R.layout.view2,null);
        View view3 = View.inflate(this,R.layout.view3,null);
        View view4 = View.inflate(this,R.layout.view4,null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        tabList.add("第一个网页");
        tabList.add("第二个网页");
        tabList.add("第三个网页");
        tabList.add("第四个网页");

        //创建pagerAdapter适配器
        MypagerAdapter adapter = new MypagerAdapter(viewList,tabList);
        padapter = (ViewPager) findViewById(R.id.Viewpager);
        padapter.setAdapter(adapter);

    }
}
