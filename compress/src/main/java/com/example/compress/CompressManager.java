package com.example.compress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by ljh on 2018/3/28.
 */

public class CompressManager {
    public static final int maxSize = 200;
    public static final int mWidth = 1080;
    public static final int mHeight = 1920;

    /**
     * options.inSampleSize只能是2的N次方，算出来是3，取近似值则是4，偏差会比较大
     * 使用canvas重绘bitmap比较精准
     */

    /**
     *
     * @param fileDescriptor
     * @return
     */
    public static Bitmap compress(FileDescriptor fileDescriptor,int width,int height){
        if(width == 0){
            width = mWidth;
        }
        if(height == 0){
            height = mHeight;
        }
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
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
        Log.i("mainActivity","scale = " + scale);

        Bitmap resultBitmap = Bitmap.createBitmap(bWidth / scale,bHeight / scale, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(resultBitmap);
        Rect rectDest = new Rect(0,0,bWidth / scale,bHeight / scale);
        canvas.drawBitmap(bitmap,null,rectDest,null);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG,quality,byteArrayOutputStream);
        while(byteArrayOutputStream.toByteArray().length / 1024 > maxSize){
            byteArrayOutputStream.reset();
            quality-=10;
            resultBitmap.compress(Bitmap.CompressFormat.JPEG,quality,byteArrayOutputStream);
            if(quality < 30){
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
        Log.i("mainActivity","compress.size = " + resultBitmap.getByteCount() / 1024);
        return resultBitmap;
    }

    public static Bitmap compress(Context context,Uri uri,int width,int height){
        if(width == 0){
            width = mWidth;
        }
        if(height == 0){
            height = mHeight;
        }

        Bitmap resultBitmap = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
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
            Log.i("mainActivity","scale = " + scale);

            resultBitmap = Bitmap.createBitmap(bWidth / scale,bHeight / scale, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(resultBitmap);
            Rect rectDest = new Rect(0,0,bWidth / scale,bHeight / scale);
            canvas.drawBitmap(bitmap,null,rectDest,null);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            resultBitmap.compress(Bitmap.CompressFormat.JPEG,quality,byteArrayOutputStream);
            while(byteArrayOutputStream.toByteArray().length / 1024 > maxSize){
                byteArrayOutputStream.reset();
                quality-=10;
                resultBitmap.compress(Bitmap.CompressFormat.JPEG,quality,byteArrayOutputStream);
            }
            if(bitmap != null){
                bitmap.recycle();
            }
            byteArrayOutputStream.reset();
            byteArrayOutputStream.close();
            Log.i("mainActivity","compress.size = " + resultBitmap.getByteCount() / 1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultBitmap;
    }

    public static Bitmap qualityCompress(FileDescriptor fileDescriptor){
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte bytes[] = byteArrayOutputStream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Log.i("mainActivity","qualityCompress.size = " + bitmap.getByteCount() / 1024);
        return bitmap;
    }

    public static Bitmap RGB_565(FileDescriptor fileDescriptor){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
        Log.i("mainActivity","RGB_565.size = " + bitmap.getByteCount() / 1024);
        return bitmap;
    }

    public static Bitmap scaleCompress(FileDescriptor fileDescriptor){
        int scale = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
        int bWidth = options.outWidth;
        int bHeight = options.outHeight;
        if(bWidth > bHeight && bWidth > 1080){
            scale = bWidth / 1080;
        }else if(bHeight > bWidth && bHeight > 1920){
            scale = bHeight / 1920;
        }
        if(scale < 1){
            scale = 1;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
        Log.i("mainActivity","scaleCompress.scale = " + scale);
        Log.i("mainActivity","scaleCompress = " + bitmap.getByteCount() / 1024);
        return bitmap;
    }

}
