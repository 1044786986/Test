package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ljh on 2017/8/12.
 */

public class GetAndPost extends Activity{
    private Button button1;
    private EditText etName;
    private EditText etAge;
    private String url = "http://192.168.1.105:8080/web/index.jsp";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.getandpost);

                this.button1 = (Button) findViewById(R.id.button1);
                this.etName = (EditText) findViewById(R.id.etName);
                this.etAge = (EditText) findViewById(R.id.etAge);

                button1.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        new HttpThread1(url,etName.getText().toString(),etAge.getText().toString()).start();
            }
        });
    }
}
