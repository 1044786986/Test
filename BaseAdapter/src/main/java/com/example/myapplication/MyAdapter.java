package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ljh on 2017/7/23.
 */

public class MyAdapter extends BaseAdapter {
    private List<ItemBean> datalist;
    private LayoutInflater layoutInflater;

    MyAdapter(Context context, List<ItemBean>datalist){
        this.datalist = datalist;
        this.layoutInflater = layoutInflater.from(context);//上下文
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
      /*  view = layoutInflater.inflate(R.layout.item, null);
        if(view == null) {  //利用了ListView缓存机制，可以判断ListView的数据是否已经加载过，节省时间和资源
            //避免了重复创建大量的view
            ImageView imageView = (ImageView) view.findViewById(R.id.lvPic);
            TextView Title = (TextView) view.findViewById(R.id.tvTitle);
            TextView Content = (TextView) view.findViewById(R.id.tvContent);

            imageView.setImageResource(datalist.get(i).ItemImageResid);
            Title.setText(datalist.get(i).ItemTitle);
            Content.setText(datalist.get(i).ItemContent);
       } */
        /**
         * 不仅利用了ListView的缓存,更通过ViewHolder类来实现显示数据的视图缓存，
         * 避免多次通过findviewById寻找控件
         */
        ViewHolder viewHolder;

        if(view == null){
            view = layoutInflater.inflate(R.layout.item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.lvPic);
            viewHolder.title = (TextView) view.findViewById(R.id.tvTitle);
            viewHolder.content = (TextView) view.findViewById(R.id.tvContent);

            view.setTag(viewHolder);//通过setTag将view和viewHolder绑定
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(datalist.get(i).ItemImageResid);
        viewHolder.title.setText(datalist.get(i).ItemTitle);
        viewHolder.content.setText(datalist.get(i).ItemContent);
        return view;
        }
        //创建内部类
        class ViewHolder {
            ImageView imageView;
            TextView title;
            TextView content;
        }


    }

