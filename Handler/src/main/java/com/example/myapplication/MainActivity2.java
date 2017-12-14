package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ljh on 2017/8/8.
 */

public class MainActivity2 extends Activity {
    private ImageView imageView;
    Handler handler = new Handler();
    MyRunnable myRunnable = new MyRunnable();
    int ImageId[] = {R.mipmap.pic1,R.mipmap.pic2,R.mipmap.pic3,R.mipmap.pic4} ;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.imageView = (ImageView) findViewById(R.id.iv);
        handler.postDelayed(myRunnable,1000);

        imageView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                handler.removeCallbacks(myRunnable);//移除post
            }
        });
    }
    class MyRunnable implements Runnable{

        public void run() {
                imageView.setImageResource(ImageId[i]);
                handler.postDelayed(myRunnable, 1000);
                i++;
                i = i%3;
        }
    }

}
