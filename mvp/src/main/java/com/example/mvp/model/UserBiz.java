package com.example.mvp.model;

/**
 * Created by ljh on 2018/3/31.
 */

public class UserBiz implements IUserBiz{
    @Override
    public void login(final String username, final String password, final ILoginListener loginListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(username.equals("123") && password.equals("123")){
                    UserBean userBean = new UserBean();
                    userBean.setUsername(username);
                    userBean.setPassword(password);
                    loginListener.loginSuccess(userBean);
                }else{
                    loginListener.loginFailed();
                }
            }
        }).start();
    }
}
