package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by ljh on 2017/11/11.
 */

public class Main2Activity extends AppCompatActivity{
    private TextView tvUsername,tvPassword,tvEmail,tvSchool,tvGander;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String email = intent.getStringExtra("email");
        String school = intent.getStringExtra("school");
        String gander = intent.getStringExtra("gander");

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvSchool = (TextView) findViewById(R.id.tvSchool);
        tvGander = (TextView) findViewById(R.id.tvGander);

        tvUsername.setText(username);
        tvPassword.setText(password);
        tvEmail.setText(email);
        tvSchool.setText(school);
        tvGander.setText(gander);
    }
}
