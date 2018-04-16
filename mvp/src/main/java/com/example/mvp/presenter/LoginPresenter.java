package com.example.mvp.presenter;

import android.os.Handler;

import com.example.mvp.model.ILoginListener;
import com.example.mvp.model.IUserBiz;
import com.example.mvp.model.UserBean;
import com.example.mvp.model.UserBiz;
import com.example.mvp.view.IMainView;

/**
 * Created by ljh on 2018/3/31.
 */

public class LoginPresenter {
    private IUserBiz mUserBiz;
    private IMainView mMainView;
    private Handler mHandler = new Handler();
    
    public LoginPresenter(IMainView mainView){
        mMainView = mainView;
        mUserBiz = new UserBiz();
    }

    public void login(){
        mMainView.showLoading();
        mUserBiz.login(mMainView.getUserName(), mMainView.getPassWord(), new ILoginListener() {
            @Override
            public void loginSuccess(UserBean userBean) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.hideLoading();
                        mMainView.toSecondActivity();
                    }
                });
            }

            @Override
            public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.hideLoading();
                        mMainView.showFailedError();
                    }
                });
            }
        });
    }

    public void clear(){
        mMainView.clearUserName();
        mMainView.clearPassWord();
    }
}
