package com.example.mvp.model;

/**
 * Created by ljh on 2018/3/31.
 */

public interface ILoginListener {
    public void loginSuccess(UserBean userBean);
    public void loginFailed();
}
