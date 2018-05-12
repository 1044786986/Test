package com.example.rxjava.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ljh on 2018/4/17.
 */

public interface IGetRequest {
    @GET("http://fy.iciba.com/")
    Call getCall();
//    Call<Bean> getCall();
}
