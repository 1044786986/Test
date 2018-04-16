package com.example.mvp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvp.R;
import com.example.mvp.presenter.LoginPresenter;

public class MainActivity extends AppCompatActivity implements IMainView{
    private EditText etUser,etPass;
    private Button btLogin,btClear;
    private ProgressDialog mProgressDialog;
    private LoginPresenter mLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mLoginPresenter = new LoginPresenter(this);
        mProgressDialog = new ProgressDialog(this);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btLogin = findViewById(R.id.btLogin);
        btClear = findViewById(R.id.btClear);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.login();
            }
        });
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.clear();
            }
        });
    }

    @Override
    public String getUserName() {
        return etUser.getText()+"";
    }

    @Override
    public String getPassWord() {
        return etPass.getText()+"";
    }

    @Override
    public void clearUserName() {
        etUser.setText("");
    }

    @Override
    public void clearPassWord() {
        etPass.setText("");
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    public void toSecondActivity() {
        Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_LONG).show();
    }
}
