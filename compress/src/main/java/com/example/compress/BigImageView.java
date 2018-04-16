package com.example.compress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ljh on 2018/3/30.
 */

public class BigImageView extends View{
    private BitmapRegionDecoder mBitmapRegionDecoder;
    private BitmapFactory.Options mOptions;
    private Bitmap mBitmap;
    private Rect mRect;
    private Paint mPaint;
    private Matrix mMatrix;

    private int mImageWidth;
    private int mImageHeight;
    private int mInScreenX;
    private int mInScreenY;
    private int mPointCount;

    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector.SimpleOnGestureListener mGestureListener =
            new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    float offSetX = distanceX;
                    float offSetY = -distanceY;

                    return true;
                }
            };



    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mPointCount = event.getPointerCount();
            if(mPointCount == 1){
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        mInScreenX = (int) event.getX();
                        mInScreenY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        moveImage((int)event.getX(),(int)event.getY());
                        break;
                }
            }else{

            }mScaleGestureDetector.onTouchEvent(event);
            return true;
        }
    };

    public BigImageView(Context context) {
        this(context,null);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = width;
        mRect.bottom = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBitmap = mBitmapRegionDecoder.decodeRegion(mRect,mOptions);
//        canvas.drawBitmap(mBitmap,0,0,mPaint);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
    }


    private void moveImage(int x,int y){
        if(x - mInScreenX > 0){
            mRect.left = mRect.left + (mInScreenX - x) * 2;
            mRect.right = mRect.right + (mInScreenX - x) * 2;
        }
        if(x - mInScreenX < 0){
            mRect.left = mRect.left + (mInScreenX - x) * 2;
            mRect.right = mRect.right + (mInScreenX - x) * 2;
        }
        if(y - mInScreenY > 0){
            mRect.top = mRect.top + (mInScreenY - y) * 2;
            mRect.bottom = mRect.bottom + (mInScreenY - y) * 2;
        }
        if(y - mInScreenY < 0){
            mRect.top = mRect.top + (mInScreenY - y) * 2;
            mRect.bottom = mRect.bottom + (mInScreenY - y) * 2;
        }
        check();
        invalidate();
        mInScreenX = x;
        mInScreenY = y;
    }

    public void setInputStream(InputStream inputStream){
        try {
            mBitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream,false);
            mImageWidth = mBitmapRegionDecoder.getWidth();
            mImageHeight = mBitmapRegionDecoder.getHeight();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void check(){
        if(mRect.right > mImageWidth){
            mRect.right = mImageWidth;
            mRect.left = mImageWidth - getWidth();
        }

        if(mRect.left < 0){
            mRect.left = 0;
            mRect.right = getWidth();
        }

        if(mRect.bottom > mImageHeight){
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight - getHeight();
        }

        if(mRect.top < 0){
            mRect.top = 0;
            mRect.bottom = getHeight();
        }
    }

    private void init(){
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mRect = new Rect();
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        this.setLongClickable(true);
        this.setOnTouchListener(mOnTouchListener);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(),
                new MyScaleGestureDetectorListener());
    }


    public class MyScaleGestureDetectorListener implements ScaleGestureDetector.OnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            mMatrix.postScale(scaleFactor,scaleFactor,detector.getFocusX(),detector.getFocusY());
            invalidate();
            Log.i("aaa","scaleFactor = " + scaleFactor);
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            mMatrix.postScale(scaleFactor,scaleFactor,detector.getFocusX(),detector.getFocusY());
            invalidate();
            Log.i("aaa","scaleFactor2 = " + scaleFactor);
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.i("aaa","scaleFactor = ");
        }
    }
}
