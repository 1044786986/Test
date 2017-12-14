package com.example.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2017/8/15.
 */

public class JsonAdapter extends BaseAdapter{
    private List<Bean> list = new ArrayList<>();

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        return null;
    }

    class ViewHolder{
        TextView Title;
        TextView Content;
        TextView id;
        TextView lea;
        ImageView imageView;
    }
}
