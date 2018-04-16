package com.example.callback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        sendRequest("https://www.baidu.com", new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseData = response.body().string();
//                Log.d("main", responseData);
//            }
//        });
        Async();
//        okHttp();
    }

    private void Async(){
        MyHttpClient myHttpClient = new MyHttpClient();
        myHttpClient.execute("https://www.baidu.com", "POST", new CallBack() {
            @Override
            public void Response(String result) {
                Log.i("aaa", "result = " + result);
            }

            @Override
            public void Failed(String result) {
                Log.i("aaa", "code = " + result);
            }
        });
        Log.i("aaa", "第二个任务");
        Log.i("aaa", "下一个任务");
    }

    private void noAsyn(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("aaa","开始请求");
                    for (int i=0;i<1000000000;i++){

                    }
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://www.baidu.com").openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setReadTimeout(5000);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    outputStreamWriter.write("世界杯");
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
                    Log.i("aaa","result = " + result);
                    Log.i("aaa","第二个任务");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Log.i("aaa","下一个任务");
    }

    private void sendRequest(final String URL, final Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .build();
        client.newCall(request).enqueue(callback);
    }

    private void okHttp(){
                Log.i("aaa","开始请求");
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .readTimeout(5000, TimeUnit.SECONDS).build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("key","123")
                        .build();

                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .post(requestBody)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("aaa","result = " + response.body().string());
                    }
                });
                Log.i("aaa","第二个任务");

                 Log.i("aaa","下一个任务");
    }
}
