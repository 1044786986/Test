package com.example.rxjava;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ljh on 2018/4/17.
 */

public class CompressManager {
    public  static final int MAX_SIZE = 200;
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1920;

    public static Bitmap comPressFromBitmap(Bitmap bitmap,int width,int height){
        if(width == 0){
            width = WIDTH;
        }
        if(height == 0){
            width = HEIGHT;
        }

        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        int scale = 1;
        int quality = 100;
        if(bWidth > bHeight && bWidth > width){
            scale = bWidth / width;
        }else if(bHeight > bWidth && bHeight > height){
            scale = bHeight / height;
        }
        if(scale <= 0){
            scale = 1;
        }

        Bitmap resultBitmap = Bitmap.createBitmap(bWidth / scale,bHeight / scale, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(resultBitmap);
        Rect rectDest = new Rect(0,0,bWidth / scale,bHeight / scale);
        canvas.drawBitmap(bitmap,null,rectDest,null);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG,quality,byteArrayOutputStream);
        while(byteArrayOutputStream.toByteArray().length / 1024 > MAX_SIZE){
            byteArrayOutputStream.reset();
            quality-=10;
            resultBitmap.compress(Bitmap.CompressFormat.JPEG,quality,byteArrayOutputStream);
            if(quality <= 30){
                break;
            }
        }

        if(bitmap != null){
            bitmap.recycle();
        }

        try {
            byteArrayOutputStream.reset();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultBitmap;
    }
}
