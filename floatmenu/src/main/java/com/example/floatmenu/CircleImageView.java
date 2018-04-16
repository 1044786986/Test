package com.example.floatmenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by ljh on 2018/3/7.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private final Paint mPaint = new Paint();
    private final Matrix mMatrix = new Matrix();
    private BitmapShader mBitmapShader;
    private Bitmap mBitmap;
    private float mScale;
    protected int r;

    public CircleImageView(Context context, Bitmap bitmap){
        super(context);
        mBitmap = bitmap;
        init();
    }

    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(r * 2 ,r * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(r,r,r,mPaint);
    }

    private void init(){
        r = FloatMenu.mScreenHeight / 30;

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mScale = (r * 2.0f) / Math.min(mBitmap.getWidth(),mBitmap.getHeight());
        mMatrix.setScale(mScale,mScale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);
        mPaint.setAntiAlias(true);
    }

    protected void setR(int r){
        this.r = r;
    }

    protected void setBitmap(Bitmap bitmap){
        this.mBitmap = bitmap;
    }

}
