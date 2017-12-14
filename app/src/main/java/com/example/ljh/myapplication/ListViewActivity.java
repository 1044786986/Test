package com.example.ljh.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by ljh on 2017/6/7.
 */

public class ListViewActivity extends Activity implements OnItemClickListener,OnScrollListener{

    private ArrayAdapter<String> arr_Adapter;
    private SimpleAdapter simp_Adapter;
    private ListView listview;
    private List<Map<String,Object>> datalist;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.listview);

        listview = (ListView) findViewById(R.id.listview);
        String [] a = {"1","2","3"};
        //Adapter(上下文，当前ListView加载的每一个列表所对应的布局文件，数据源)
        arr_Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
        //listview.setAdapter(arr_Adapter);
        /* simp_Adapter = new SimpleAdapter(context,data,resourse,from,to)
           context:上下文
           data:数据源（<List <? extends Map<String,?> > data) 一个Map所组成的List集合
                每一个Map都会去对应ListView列表中的一行
                每个Map中的键都必须包含所有在from中的指定键
           resource:列表项的布局文件ID
           from:Map中的键名
           to:绑定数据视图中的ID，与from形成对应关系
         */
        datalist = new ArrayList<Map<String,Object>>();
        simp_Adapter = new SimpleAdapter(this,getData(),R.layout.item,new String[]{"pic","text"},new int []{R.id.pic,R.id.text});
        listview.setAdapter(simp_Adapter);
        listview.setOnItemClickListener(this);//单个点击监听
        listview.setOnScrollListener(this);//滚动监听
    }

    private List<Map<String,Object>> getData(){  //获取数据源方法
        for(int i = 0;i<50;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("pic",R.mipmap.ic_launcher);
            map.put("text","慕课网"+i);
            datalist.add(map);
        }
        return datalist;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) { //滚动监听事件
        switch(i){
            case SCROLL_STATE_FLING:
                Log.i("tag","手指离开键盘惯性继续滚动屏幕");
                Map<String,Object>map = new HashMap<String,Object>();
                map.put("pic",R.mipmap.ic_launcher);
                map.put("text","正在加载");
                datalist.add(map);
                simp_Adapter.notifyDataSetChanged();
                break;
            case SCROLL_STATE_IDLE:
                Log.i("tag","手指离开屏幕");
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("tag","手指正在滑动");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //单机监听事件
        String text = listview.getItemAtPosition(i) +""; //获取内容
        Toast.makeText(this,i + ":" + text,Toast.LENGTH_SHORT).show();

    }
}
