package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2017/12/4.
 */

public class Main2Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static String url = "http://www.imooc.com/api/teacher?type=4&num=30";
    List<Bean> datalist = new ArrayList<Bean>();
    private Handler handler = new Handler();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        getJsonData();
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }

    private void getJsonData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //String jsonString = readStream( new URL(url).openStream());
                    //String jsonString = readStream(new URL(url).openConnection().getInputStream());
                    URLConnection urlConnection = new URL(url).openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    String jsonString = readStream(inputStream);
                    JSONObject jsonObject;
                    Bean bean;

                    jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.i("tag", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        bean = new Bean();
                        bean.IconUrl = jsonObject.getString("picSmall");
                        bean.title = jsonObject.getString("name");
                        bean.content = jsonObject.getString("description");
                        datalist.add(bean);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String readStream(InputStream inputStream) {
        Log.i("tagggggggggg", "readStream");
        String result = "";
        InputStreamReader inputStreamReader;
        try {
            String line = "";
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");//字节流转换为字符流
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            inputStream.close();
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Main2Activity.this).inflate(R.layout.item, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.imageView.setImageBitmap(getBitmapFromUrl(datalist.get(position).IconUrl));
            holder.tvTitle.setText(datalist.get(position).title);
            holder.tvContent.setText(datalist.get(position).content);
        }

        @Override
        public int getItemCount() {
            return datalist.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvContent;
            TextView tvTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) findViewById(R.id.ivImage);
                tvTitle = (TextView) findViewById(R.id.textView);
                tvContent = (TextView) findViewById(R.id.textView2);
            }
        }
    }
}
