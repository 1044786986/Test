package com.example.myapplication;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etUsername,etPassword,etPassword2,etEmail;
    private Button btBirthday,btRegister;
    private AppCompatRadioButton rbMale,rbWoman;
    private TextView tvBirthday;
    private AppCompatAutoCompleteTextView tvSchool;

    private String username,password,password2,email,birthday,gander,school;
    private String data[] = {"中山大学","华南理工大学","华南师范大学","广东东软学院"};

    private final int year = 2000;
    private final int month = 01;
    private final int day = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btBirthday:
                getBirthday();
                break;
            case R.id.btRegister:
                Check();
                break;
        }
    }

    public void Check(){
        username = etUsername.getText()+"";
        password = etPassword.getText()+"";
        password2 = etPassword2.getText()+"";
        email = etEmail.getText()+"";
        school = tvSchool.getText()+"";
        birthday = tvBirthday.getText()+"";
        getGander();

        if(username == null || username == ""){
            showDialog();
        }else if(password == null || password == ""){
            showDialogPassWord();
        }else if(!password.equals(password2)){
            showDialogPassWord2();
        }else if(email == null || email.indexOf(".") == -1 || email.indexOf("@") == -1 ){
            showDialogEmail();
        }
        else if(!email.matches("/^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$/")){
            showDialogEmail();
        }
        else if(!rbMale.isChecked() && !rbWoman.isChecked()){
            showDialogGander();
        }
        else{
            Intent intent = new Intent(this,Main2Activity.class);
            intent.putExtra("username",username);
            intent.putExtra("password",password);
            intent.putExtra("email",email);
            intent.putExtra("gander",gander);
            intent.putExtra("birthday",birthday);
            intent.putExtra("school",school);
            startActivity(intent);
        }
    }

    public String getGander(){
        gander = null;
        if(rbMale.isChecked()){
            gander = "男";
        }else if(rbWoman.isChecked()){
            gander = "女";
        }
        return gander;
    }

    public void getBirthday(){

        new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvBirthday.setText(year + "-" + (month+1) + "-" + dayOfMonth);
            }
        },year,month,day).show();
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("请输入用户名");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void showDialogPassWord(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("请输入正确的密码格式");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void showDialogPassWord2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("两次输入的密码不一致");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showDialogEmail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("请输入正确的邮箱格式");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showDialogGander(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("请选择你的性别");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void initView(){
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        etEmail = (EditText) findViewById(R.id.etEmail);


        btBirthday = (Button) findViewById(R.id.btBirthday);
        btRegister = (Button) findViewById(R.id.btRegister);
        btBirthday.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        rbMale = (AppCompatRadioButton) findViewById(R.id.rbMale);
        rbWoman = (AppCompatRadioButton) findViewById(R.id.rbWoman);

        tvBirthday = (TextView) findViewById(R.id.tvBirthday);
        tvSchool = (AppCompatAutoCompleteTextView) findViewById(R.id.tvSchool);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        tvSchool.setAdapter(arrayAdapter);

    }


}
