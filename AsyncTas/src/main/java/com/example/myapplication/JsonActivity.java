package com.example.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2017/7/25.
 */

public class JsonActivity extends Activity{
    private ListView listView;
    private static String url = "http://www.imooc.com/api/teacher?type=4&num=30";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        listView = (ListView) findViewById(R.id.lvJson);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);

    }



    /**
     * 将url对应的JSON格式数据转化为我们所封装的Bean
     * @param url
     * @return
     */
    private List<Bean> getJsonData(String url){
        List<Bean> datalist = new ArrayList<Bean>();
        Log.i("tagggggg","getJsonData");
        try {
            //String jsonString = readStream( new URL(url).openStream());
            //String jsonString = readStream(new URL(url).openConnection().getInputStream());
            URLConnection urlConnection =  new URL(url).openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            String jsonString = readStream(inputStream);
            JSONObject jsonObject;
            Bean bean;
            try {
               jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                Log.i("tag",jsonArray.toString());
                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    bean = new Bean();
                    bean.IconUrl = jsonObject.getString("picSmall");
                    bean.title = jsonObject.getString("name");
                    bean.content = jsonObject.getString("description");
                    datalist.add(bean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("tag","datalist的长度是" + datalist.size());
        return datalist;
    }

    /**
     * 通过InputStream解析网页返回的数据
     * @param //inputStream
     * @return
     */
    private String readStream(InputStream inputStream){
        Log.i("tagggggggggg","readStream");
        String result = "";
        InputStreamReader inputStreamReader;
        try {
            String line = "";
            inputStreamReader = new InputStreamReader(inputStream,"utf-8");//字节流转换为字符流
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ( (line = bufferedReader.readLine()) != null){
                result += line;
            }
            inputStreamReader.close();
            bufferedReader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 实现网络的异步访问
     */
    class MyAsyncTask extends AsyncTask<String,Void,List<Bean>>{

        @Override
        protected List<Bean> doInBackground(String... strings) {
            Log.i("tagggggg","doInBackground");
            return getJsonData(strings[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("tagggggg","onPreExecute");
        }

        @Override
        protected void onPostExecute(List<Bean> datalist) {
            super.onPostExecute(datalist);
            MyBaseAdapter adapter = new MyBaseAdapter(JsonActivity.this,datalist,listView);
            listView.setAdapter(adapter);
            Log.i("tagggggg","onPostExecute");
        }
    }

}
