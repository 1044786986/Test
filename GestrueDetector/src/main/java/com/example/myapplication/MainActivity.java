package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    GestureDetector gestureDetector;
    MyGestureDetector myGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageView = (ImageView) findViewById(R.id.iv);
        myGestureDetector = new MyGestureDetector();
        gestureDetector = new GestureDetector(MainActivity.this,myGestureDetector);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            //可以捕获的触发屏幕发生的event时间
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }
    public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() < 50){
                Toast.makeText(MainActivity.this,"向右滑动",Toast.LENGTH_SHORT).show();
            }else if(e1.getX() - e2.getX() > 50){
                Toast.makeText(MainActivity.this,"向左滑动",Toast.LENGTH_SHORT).show();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }


}
