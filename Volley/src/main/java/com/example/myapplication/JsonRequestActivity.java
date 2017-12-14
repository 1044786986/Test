package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2017/8/18.
 */

public class JsonRequestActivity extends Activity{
    private String url = "http://www.imooc.com/api/teacher?type=4&num=30";
    private ListView listView;
    List<Bean> list = new ArrayList<Bean>();
    //List<Bean> list;
    RequestQueue requestQueue;
    Handler handler;
    Handler handler1 = new Handler();
    MyAdapter adapter;
   /* private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list = (List<Bean>) msg.obj;
            Log.i("tag","msg.obj的长度是" + list.size());
            Log.i("tag","我接受到Target啦!");
        }
    };*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonrequest);
        Log.i("tag","---------onCreate");
        // requestQueue = Volley.newRequestQueue(JsonRequestActivity.this);
        this.listView = (ListView) findViewById(R.id.lv);

       /* new Thread() {
            @Override
            public void run() {
                super.run();
                getJsonData(url);
                Log.i("tag", "taggggggg" + list.size());
                MyAdapter adapter = new MyAdapter(JsonRequestActivity.this, list, listView);
                listView.setAdapter(adapter);
                Log.i("tag", "我运行了onCreate里面的Thread啦！");
            }
        }.start();*/
          new Thread(){
              @Override
              public void run() {
                  getJsonData(url);
                  Log.i("tag","我运行了getJsonData");
              }
          }.start();

          handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                list = (List<Bean>) msg.obj;
                Log.i("tag","msg.obj的长度是" + list.size());
                Log.i("tag","我接受到Target啦!");

            new Thread(){
                @Override
                public void run() {
                    adapter = new MyAdapter(JsonRequestActivity.this, list, listView);
                }
            }.start();
            }
        };
        handler1.post(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);
                Log.i("tag","运行了啦");
            }
        });

    }
    public void getJsonData(String url){
        //List<Bean> list = new ArrayList<Bean>();

        requestQueue = Volley.newRequestQueue(JsonRequestActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject jsonObject) {

                        //Log.i("tag",jsonObject.toString());
                        try {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            /**
                             *
                             */
                            Log.i("tag","jsonArray.toString" + jsonArray.toString());

                            for(int i = 0;i<jsonArray.length();i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                Bean bean = new Bean();
                                bean.setImageId(jsonObject.getString("picSmall"));
                                bean.setTitle(jsonObject.getString("name"));
                                bean.setContent(jsonObject.getString("description"));
                                list.add(bean);
                            }
                            new Thread(){
                                @Override
                                public void run() {
                                    Message message = handler.obtainMessage();
                                    message.obj = list;
                                    message.sendToTarget();
                                    Log.i("tag","我sendToTarget啦！");

                                }
                            }.start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("tag","list集合是长的吗" + list.size());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("tag",volleyError.getMessage(),volleyError);
                    }
                }

        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("tag","----------onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("tag","-------onStart");
    }

    class Handler2 extends Handler{
        JsonRequestActivity jsonRequestActivity = new JsonRequestActivity();

    }
}
