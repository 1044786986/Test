package com.example.ljh.myapplication;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ljh on 2017/7/13.
 */

public class MypagerAdapter extends PagerAdapter {
    private List<View>viewList;
    private List<String>tabList;

    MypagerAdapter(List<View>viewList,List<String>tabList){
        this.tabList = tabList;
        this.viewList = viewList;
    }
    //返回页卡的数量
    @Override
    public int getCount() {
        return viewList.size();
    }
    //View是否来自于对象
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    //实例化一个页卡
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
    //销毁一个页卡
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(viewList.get(position));

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }
}
