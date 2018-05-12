package com.example.ljh.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ljh on 2018/4/21.
 */

public class ViewAndViewGroup extends Activity{
    private Button button;
    private ImageView imageView;
    private TextView textView;
    private LinearLayout linearLayout,myLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewandviewgroup);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        myLinearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);

        myLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("aaa","myLinearLayout.Down()");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("aaa","myLinearLayout.Move()");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("aaa","myLinearLayout.Up()");
                        break;
                }
                return false;
            }
        });

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("aaa","linearLayout.Down()");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("aaa","linearLayout.Move()");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("aaa","linearLayout.Up()");
                        break;
                }
                return false;
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aaa","linearLayout.onClick()");
            }
        });


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("aaa","button.onClick()");
//            }
//        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("aaa","button.Down()");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("aaa","button.Move()");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("aaa","button.Up()");
                        break;
                }
                return false;
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("aaa","imageView.Down()");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("aaa","imageView.Move()");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("aaa","imageView.Up()");
                        break;
                }
//                Log.d("aaa","imageView.onTouch()");
                return false;
            }
        });

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("aaa","imageView.OnClick()");
//            }
//        });

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("aaa","textView.onClick()");
//            }
//        });

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("aaa","textView.Down()");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("aaa","textView.Move()");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("aaa","textView.Up()");
                        break;
                }
                return false;
            }
        });
    }
}
