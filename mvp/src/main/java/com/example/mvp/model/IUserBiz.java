package com.example.mvp.model;

/**
 * Created by ljh on 2018/3/31.
 */

public interface IUserBiz {
    public void login(String username,String password,ILoginListener longListener);
}
