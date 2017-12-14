package com.example.myapplication;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ItemBean> datalist = new ArrayList<ItemBean>();
        for(int i =0;i<20;i++){
            datalist.add(new ItemBean(R.mipmap.ic_launcher,"标题"+ i,"内容" + i));
        }
        MyAdapter myAdapter = new MyAdapter(this,datalist);
        listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(myAdapter);
    }
}
