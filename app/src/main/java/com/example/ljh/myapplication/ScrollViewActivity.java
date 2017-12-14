package com.example.ljh.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ljh on 2017/7/15.
 */

public class ScrollViewActivity extends Activity{
    private ScrollView scrollView;
    private TextView textView;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_scrollview);

        textView = (TextView) findViewById(R.id.scrollview_textview);
        textView.setText(R.string.content);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    //getScrollY()滚动条的滚动距离
                    //getMeasureHeight
                    //getHeight
                    case MotionEvent.ACTION_MOVE:
                        if(scrollView.getChildAt(0).getMeasuredHeight() ==
                                scrollView.getHeight() + scrollView.getScrollY()){
                            Toast.makeText(ScrollViewActivity.this,"加载更多",Toast.LENGTH_SHORT).show();
                            textView.append(getResources().getString(R.string.content));
                        }
                        break;
                }
                return false;
            }
        });
    }
}
