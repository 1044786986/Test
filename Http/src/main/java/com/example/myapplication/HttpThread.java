package com.example.myapplication;

import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ljh on 2017/8/11.
 */

public class HttpThread extends Thread {
    private String url;
    private WebView webView;
    private Handler handler;

    HttpThread(String url,WebView webView,Handler handler){
        this.url = url;
        this.webView = webView;
        this.handler = handler;
    }


    public void run() {
        super.run();
        try {
            HttpURLConnection httpURLConnection= (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setReadTimeout(5000);//请求超时的时间
            httpURLConnection.setRequestMethod("GET");//网页请求的方式
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            //简便写法
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            final StringBuffer stringBuffer = new StringBuffer();

            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            handler.post(new Runnable() {

                public void run() {
                    webView.loadDataWithBaseURL(url,stringBuffer.toString(),"text/html","utf-8",null);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
