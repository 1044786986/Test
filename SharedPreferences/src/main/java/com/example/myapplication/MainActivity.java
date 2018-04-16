package com.example.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText etUserName;
    private EditText etPassWord;
    private CheckBox cbUserName;
    private CheckBox cbPassWord;
    private Button btLogin;
    private SharedPreferences sharedPreferences;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassWord = (EditText) findViewById(R.id.etPassWord);
        cbUserName = (CheckBox) findViewById(R.id.cbUserName);
        cbPassWord = (CheckBox) findViewById(R.id.cbPassWord);
        btLogin = (Button) findViewById(R.id.btLogin);

        sharedPreferences = getSharedPreferences("MySharePreferences",MODE_PRIVATE);

        UserInfo();
        String UserName = sharedPreferences.getString("UserName","");
        String PassWord = sharedPreferences.getString("PassWord","");

        if(PassWord.equals("")){
            cbPassWord.setChecked(false);
        }else{
            etPassWord.setText(PassWord);
            cbPassWord.setChecked(true);
        }
        if(UserName.equals("")){
            cbUserName.setChecked(false);
        }else{
            etUserName.setText(UserName);
            cbUserName.setChecked(true);
        }


    }
    public void UserInfo(){

        cbPassWord.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if(cbPassWord.isChecked()) {
                    cbUserName.setChecked(true);
                }
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String UserName = etUserName.getText().toString();
                String PassWord = etPassWord.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(UserName.equals("admin") && PassWord.equals("123")){

                    if(cbPassWord.isChecked()){
                        cbUserName.setChecked(true);
                        editor.putString("PassWord",PassWord);
                        editor.commit();
                    }else{
                        editor.remove("PassWord");
                        editor.commit();
                    }
                    if(cbUserName.isChecked()){
                        editor.putString("UserName",UserName);
                        editor.commit();
                    }else {
                        editor.remove("UserName");
                        editor.commit();
                    }
                    Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"登录失败,用户名或密码不正确",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void a(){
//        List<Integer> list1 = new ArrayList<>();
//        list1.add("舒勋");
    }



}
