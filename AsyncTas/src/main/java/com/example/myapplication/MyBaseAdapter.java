package com.example.myapplication;

import android.content.Context;
import android.media.Image;
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
 * Created by ljh on 2017/7/25.
 */

public class MyBaseAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    List<Bean> datalist = new ArrayList<Bean>();
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    private int mStart,mEnd;
    static String []urls;
    private boolean first_flag;

    MyBaseAdapter(Context context, List<Bean>datalist,ListView listView){
        this.layoutInflater = layoutInflater.from(context);
        this.datalist = datalist;

        this.imageLoader = new ImageLoader(listView);

        urls = new String[datalist.size()];
    for(int i =0;i<datalist.size();i++){
        urls[i] = datalist.get(i).IconUrl;
    }
    first_flag = true;

    listView.setOnScrollListener(this);
}

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int i) {
        return datalist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i("tagggggg","getVIEW");
        ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.ivImage);
            viewHolder.title = (TextView) view.findViewById(R.id.textView);
            viewHolder.content = (TextView) view.findViewById(R.id.textView2);
            Log.i("tag","view == null");
            view.setTag(viewHolder);//将view视图与viewHolder绑定
        }else{
            viewHolder = (ViewHolder) view.getTag();
            Log.i("tag","view != null");
        }
        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        String url = datalist.get(i).IconUrl;
        viewHolder.imageView.setTag(url);   //将图片url与对应的viewHolder(item)绑定
        //ImageLoader imageLoader = new ImageLoader();
        //imageLoader.showImageByThread(viewHolder.imageView,url);//多线程方式加载图片
        //imageLoader.showImageByAsyncTask(viewHolder.imageView,url);//异步加载图片
        imageLoader.showImageByAsyncTask(viewHolder.imageView,url);//缓存加载图片
        viewHolder.title.setText(datalist.get(i).title);
        viewHolder.content.setText(datalist.get(i).content);
        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if(i == SCROLL_STATE_IDLE){
            //加载可见项
            imageLoader.loadImages(mStart,mEnd);
        }else {
            //停止任务
           // imageLoader.cancel();
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        mStart = i;
        mEnd = i + i1;
        if(first_flag == true && i1 > 0){
            imageLoader.loadImages(mStart,mEnd);
            first_flag = false;
        }
    }

    class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView content;
    }
}
