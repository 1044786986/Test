package com.example.mvp.view;

/**
 * Created by ljh on 2018/3/31.
 */

public interface IMainView {
    public String getUserName();
    public String getPassWord();
    public void clearUserName();
    public void clearPassWord();
    public void showLoading();
    public void hideLoading();
    public void toSecondActivity();
    public void showFailedError();
}
