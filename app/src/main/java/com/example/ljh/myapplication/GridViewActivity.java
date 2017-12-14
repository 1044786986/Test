package com.example.ljh.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljh on 2017/6/15.
 */

public class GridViewActivity extends Activity implements AdapterView.OnItemClickListener {
    private GridView gridview;
    private List<Map<String,Object>> datalist;
    private int[]icon={R.mipmap.address_book, R.mipmap.calendar, R.mipmap.camera, R.mipmap.clock,
            R.mipmap.games_control, R.mipmap.ic_launcher, R.mipmap.messenger, R.mipmap.ringtone,
            R.mipmap.settings, R.mipmap.speech_balloon, R.mipmap.weather, R.mipmap.world,
            R.mipmap.youtube};
    private String[]iconName={"通讯录", "日历", "照相机", "时钟", "游戏", "启动", "短信", "铃声", "设置",
            "语音", "天气", "浏览器", "视频"};
    private SimpleAdapter simp_Adapter;
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_gridview);
        //1.准备数据源
        //2.新建适配器(simpleAdapter)
        //3.GridView加载适配器
        //4.GridView配置事件监听器(OnItemClickListener)

        datalist = new ArrayList<Map<String,Object>>();
       // simp_Adapter = (SimpleAdapter)findViewById(layout)
        gridview = (GridView)findViewById(R.id.GridView);
        simp_Adapter = new SimpleAdapter(this,getData(),R.layout.gridview,new String[]{"pic","text"},
                new int[]{R.id.ImageView,R.id.TextView});
        gridview.setAdapter(simp_Adapter);
        gridview.setOnItemClickListener(this);



    }
    private List<Map<String,Object>> getData(){
        for(int i=0;i<iconName.length;i++) {
            Map<String,Object>map = new HashMap<String,Object>();
            map.put("pic",icon[i]);
            map.put("text",iconName[i]);
            datalist.add(map);
        }
        return datalist;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String text = gridview.getItemAtPosition(i)+"";
        Toast.makeText(this,i+":"+text,Toast.LENGTH_SHORT);
       //Toast.makeText(this,"我是" + iconName[i],Toast.LENGTH_SHORT).show();
      /*  Intent intent = new Intent(GridViewActivity.this,MainActivity.class);
        startActivity(intent);*/
    }
}