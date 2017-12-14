package com.example.myapplication;

import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ljh on 2017/8/12.
 */

public class HttpThread1 extends Thread{
    private String url;
    private String Name;
    private String Age;

    public HttpThread1(String url,String Name,String Age){
        this.url = url;
        this.Name = Name;
        this.Age = Age;
    }
    private void Get(){
        url = url + "?name=" + Name + "&age=" + Age;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestMethod("Get");
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            StringBuffer stringBuffer = new StringBuffer();

            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            Log.i("info",stringBuffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void Post(){
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("Post");
            httpURLConnection.setReadTimeout(5000);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String content = "name=" + Name + "&age=" + Age;
            String line = "";
            StringBuffer stringBuffer = new StringBuffer();
            outputStream.write(content.getBytes());

            while((line=bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            Log.i("info",stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        //Get();
        Post();
    }
}
