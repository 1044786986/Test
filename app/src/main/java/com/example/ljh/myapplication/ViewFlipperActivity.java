package com.example.ljh.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * Created by ljh on 2017/7/15.
 */

public class ViewFlipperActivity extends Activity {
    private ViewFlipper viewFlipper;
    private int[] resId = {R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4};
    private float startX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        //动态导入的方式ViewFlipper加入子View
        for (int i = 0; i < resId.length; i++) {
            viewFlipper.addView(getImageView(resId[i]));
        }
        //为ViewFlipper添加动画效果
      /*  viewFlipper.setInAnimation(this, R.anim.left_in);
        viewFlipper.setOutAnimation(this, R.anim.left_out);
        //设置切换的时间间隔
        viewFlipper.setFlipInterval(3000);
        //开始播放
        viewFlipper.startFlipping();*/
    }

    private ImageView getImageView(int resId) {
        ImageView image = new ImageView(this);
        // image.setImageResource(resId);
        image.setBackgroundResource(resId);
        //.setImageDrawable();
        return image;
    }
    //手势滑动屏幕
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() - startX > 100) {
                    viewFlipper.setInAnimation(this, R.anim.left_in);
                    viewFlipper.setOutAnimation(this, R.anim.left_out);
                    viewFlipper.showPrevious();//显示前一页
                    Toast.makeText(ViewFlipperActivity.this, "向右滑动", Toast.LENGTH_SHORT).show();
                } else if (startX - event.getX() > 100) {
                    viewFlipper.setInAnimation(this, R.anim.right_in);
                    viewFlipper.setOutAnimation(this, R.anim.right_out);
                    viewFlipper.showNext();//显示后一页
                    Toast.makeText(ViewFlipperActivity.this, "向左滑动", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onTouchEvent(event);
    }


}
