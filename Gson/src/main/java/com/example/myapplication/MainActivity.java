package com.example.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private String url = "http://www.imooc.com/api/teacher?type=4&num=30";
    private List<Bean> datalist = new ArrayList<Bean>();
    Bean bean = new Bean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(){
            @Override
            public void run() {
                getJsonData(url);
            }
        }.start();
    }

    public void getJsonData(String url){

        try {
            OkHttpClient okHttpClient  = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            String JsonData = response.body().string();
            Log.i("tag","Json的总数据是" + JsonData);

            Gson gson = new Gson();
            //JsonObject jsonObject = new JsonObject();
            //JsonArray jsonArray = jsonObject.getAsJsonArray("data");
            try {
                JSONObject jsonObject = new JSONObject(JsonData);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                datalist = gson.fromJson(jsonArray.toString(),new TypeToken<List<Bean>>(){}.getType());
                Log.i("tag","datalist的数据是" + datalist.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
