package com.example.myapplication;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by ljh on 2017/8/19.
 */

public class BitmapCache implements ImageLoader.ImageCache{
    private LruCache<String,Bitmap> lruCache;
    int max = (int) Runtime.getRuntime().maxMemory();
    int Cache = max / 4;

    BitmapCache(){
        lruCache = new LruCache<String,Bitmap>(Cache){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    public Bitmap getBitmap(String s) {
        return lruCache.get(s);
    }

    public void putBitmap(String s, Bitmap bitmap) {
        lruCache.put(s,bitmap);
    }
}
