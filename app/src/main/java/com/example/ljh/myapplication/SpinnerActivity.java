package com.example.ljh.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView.OnScrollListener;
/**
 * Created by ljh on 2017/7/10.
 */

public class SpinnerActivity extends Activity implements OnItemSelectedListener {
    private TextView textview;
    private Spinner spinner;
    private List<String> list;
    private List<Map<String,Object>> datalist;
    private ArrayAdapter  adapter;
    private SimpleAdapter sim_adapter;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_spinner);

      String num[] = {"pic","text"};
        int num2[] = {R.id.imageview1,R.id.textview1};
        textview = (TextView) findViewById(R.id.textview);
        spinner = (Spinner) findViewById(R.id.spinner);
        this.list = new ArrayList<String>();
        this.datalist = new ArrayList<Map<String,Object>>();
        textview.setText("你选择了北京");
        //设置数据源
        list.add("北京");
        list.add("上海");
        list.add("广州");
        list.add("深圳");
        //新建ArrayAdapter(数组适配器)
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        sim_adapter = new SimpleAdapter(this,getData(),R.layout.item2,num,num2);
        //adapter设置一个下拉列表样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner加载适配器
        spinner.setAdapter(sim_adapter);
        //spinner设置监听器
        spinner.setOnItemSelectedListener(this);
        //spinner.setOnScrollChangeListener((View.OnScrollChangeListener)this);
    }
    private List<Map<String,Object>> getData(){
        for(int i=0;i<10;i++){
            Map<String,Object>map = new HashMap<String,Object>();
            map.put("pic",R.mipmap.ic_launcher);
            map.put("text","哈哈哈"+i);
            datalist.add(map);
        }
        return datalist;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Map<String,Object> cityName = datalist.get(i);
        textview.setText("你选择了" + cityName);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

  /*  @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }*/
}
