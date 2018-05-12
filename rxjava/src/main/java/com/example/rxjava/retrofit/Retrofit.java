package com.example.rxjava.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ljh on 2018/4/17.
 */

public class Retrofit extends AppCompatActivity{
    private Flowable flowable;
    private Subscriber subscriber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        request();
        request2();
        flowable.subscribe(subscriber);
    }

    private void request(){
        //创建retrofit对象
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://www.baidu.com/")
//                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建网络请求接口实例
        IGetRequest request = retrofit.create(IGetRequest.class);

        //对发送请求进行封装
//        Call<Bean> call = request.getCall();

//        Call call = request.getCall();
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.d("aaa","response = " + response.body());
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                Log.d("aaa","连接失败 == " + t);
//            }
//        });
//        call.enqueue(new Callback<>() {
//            @Override
//            public void onResponse(Call<Bean> call, Response<Bean> response) {
//                response.body().show();
//            }
//
//            @Override
//            public void onFailure(Call<Bean> call, Throwable t) {
//                Log.d("aaa","连接失败 == " + t);
//            }
//        });
    }

    private void request2(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.SECONDS)
                .connectTimeout(5000,TimeUnit.SECONDS)
                .build();
        FormBody requestBody = new FormBody.Builder()
                .build();
        final Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build();
        final Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("aaa","onFailure = " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("aaa","response = " + response.body().string());
            }
        };

         flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(final FlowableEmitter<String> emitter) throws Exception {
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                        Log.d("aaa","onFailure = " + e);
                        emitter.onNext(e+"");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.d("aaa","response = " + response.body().string());
                        emitter.onNext(response.body().string());
                    }
                });
//                okHttpClient.newCall(request).enqueue(callback);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread());

        subscriber = new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Object o) {
                Log.d("aaa","onNext = " + o);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
