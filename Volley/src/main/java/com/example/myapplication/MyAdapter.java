package com.example.myapplication;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2017/8/18.
 */

public class MyAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private List<Bean> list;
    private LayoutInflater layoutInflater;
    private int mStart,mEnd;
    ImageLoad imageLoad;
    static String urls[];
    private Boolean first_flag;

    MyAdapter(Context context, List<Bean> list, ListView listView){
        this.layoutInflater = layoutInflater.from(context);
        this.list = list;
        Log.i("tag","MyAdapter的list是" + list.size());
        //imageLoad = new ImageLoad(listView);
        first_flag = true;

        urls = new String[list.size()];
        for(int i=0;i<list.size();i++){
            urls[i] = list.get(i).getImageId();
        }
        listView.setOnScrollListener(this);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int i) {
        return list.get(i);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = layoutInflater.inflate(R.layout.item,null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.ivImage);
            viewHolder.title = (TextView) view.findViewById(R.id.textView);
            viewHolder.content = (TextView) view.findViewById(R.id.textView2);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
            //viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
            viewHolder.title.setText(list.get(i).getTitle());
            viewHolder.content.setText(list.get(i).getContent());

        return view;
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
        if(i == SCROLL_STATE_IDLE){
            imageLoad.LoadImage(mStart,mEnd);
        }
    }

    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        mStart = i;
        mEnd = i + i1;

        if(first_flag && i1 > 0){
            imageLoad.LoadImage(mStart,mEnd);
            first_flag = false;
        }
    }

    class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView content;
    }
}
