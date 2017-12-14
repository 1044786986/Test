package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ljh on 2017/7/30.
 */

public class FileActivity extends Activity{
    private EditText etFile;
    private Button btFile;
    private TextView tvFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        this.etFile = (EditText) findViewById(R.id.etFile);
        this.btFile = (Button) findViewById(R.id.btFile);
        this.tvFile = (TextView) findViewById(R.id.tvFile);

        btFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etFile.getText().toString();
                WriteFile(content);
                tvFile.setText(ReadFile());
            }
        });
    }
    //保存文件内容
    public void WriteFile(String content){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("a.txt",MODE_PRIVATE);//用于将字节数据写出到文件中
            fileOutputStream.write(content.getBytes());// 将 b.length 个字节从指定 byte 数组写入此文件输出流中
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String ReadFile(){
        String content = null;
        try {
            FileInputStream fileInputStream = openFileInput("a.txt");//用于读取本地文件中的字节数据
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//可以捕获内存缓冲区的数据，转换成字节数组。
            byte [] buffer = new byte[1024];
            int len = 0;
            while((len = fileInputStream.read(buffer)) != -1){   //将最多buffer.length个字节数据读入buffer数组中
                //write(byte[],int off,int len)
                byteArrayOutputStream.write(buffer,0,len);//将指定buffer数组中off开始的len个字节写入此文件输出流
            }
            content = byteArrayOutputStream.toString();
            fileInputStream.close();
            byteArrayOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return content;
    }



}
