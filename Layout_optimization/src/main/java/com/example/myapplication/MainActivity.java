package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{
    private TextView textView;
    private Button button;
    private ViewStub viewStub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview1);
        button = (Button) findViewById(R.id.button1);
        viewStub = (ViewStub) findViewById(R.id.viewstub);
        textView.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      /*  Intent intent = new Intent(Intent.ACTION_VIEW);
       intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);*/
        viewStub.inflate();

    }
}
