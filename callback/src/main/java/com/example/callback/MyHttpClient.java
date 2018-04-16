package com.example.callback;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ljh on 2018/4/1.
 */

public class MyHttpClient {

    public void execute(final String url,final String method,final CallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("aaa","开始请求");
                    for (int i=0;i<1000000000;i++){

                    }
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                    httpURLConnection.setRequestMethod(method);
                    httpURLConnection.setReadTimeout(5000);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    outputStreamWriter.write(method);
                    outputStreamWriter.flush();
                    outputStream.close();
                    outputStreamWriter.close();

                    int responseCode = httpURLConnection.getResponseCode();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = "";
                    StringBuffer result = new StringBuffer();
                    while ((line = bufferedReader.readLine()) != null){
                        result.append(line);
                    }

                    if(responseCode == 200){
                        callBack.Response(result+"");
                    }else{
                        callBack.Failed(responseCode+"");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
