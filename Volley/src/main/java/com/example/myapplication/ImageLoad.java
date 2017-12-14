package com.example.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.LoginFilter;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by ljh on 2017/8/18.
 */

public class ImageLoad extends Activity{
    private LruCache<String,Bitmap> lruCache;
    private RequestQueue requestQueue;
    private int maxMemory = (int) Runtime.getRuntime().maxMemory();
    private int CacheSize = maxMemory / 4;
    private ImageView imageView;
    final String url = "http://pic3.zhongsou.com/image/38063b6d7defc892894.jpg";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("tag","msg.obj = " + msg.obj);
            Bitmap bitmap = (Bitmap) msg.obj;
            imageView.setImageBitmap(bitmap);
        }
    };
    private ListView listView;
    ImageLoad(ListView listView){
        this.listView = listView;


        lruCache = new LruCache<String,Bitmap>(CacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }


   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageload);

        imageView = (ImageView) findViewById(R.id.imageView);
        requestQueue = Volley.newRequestQueue(ImageLoad.this);

        lruCache = new LruCache<String,Bitmap>(CacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
       /* new Thread(){
            @Override
            public void run() {
               final Bitmap bitmap = getImageFromUrl(url);
                Log.i("tag","运行了子线程" + bitmap);
             handler.post(new Runnable() {
                 @Override
                 public void run() {
                     Log.i("tag","运行了主线程");
                    imageView.setImageBitmap(bitmap);
                 }
             })  ;
            }
        }.start();
        getImageFromUrl(url);
    }*/

    public void LoadImage(int start, int end){
        for(int i = start;i<end;i++){
           final String url = MyAdapter.urls[i];
            if(getBitmapFromCache(url) == null){

                new Thread(){
                    @Override
                    public void run() {
                       // putBitmap(url,bitmap);
                        final ImageView mImageView = (ImageView) listView.findViewWithTag(url);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                              //  mImageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }.start();
            }
        }

    }
    /*public ImageLoader getImageFromUrl(String url){
        Bitmap bitmap = null;
        String mUrl = url;
         ImageLoader imageLoader = new ImageLoader(requestQueue,new BitmapCache());


        //调用ImageLoader的getImageListener()方法能够获取到一个ImageListener对象，
        // getImageListener()方法接收三个参数，第一个参数指定用于显示图片的ImageView控件，
        // 第二个参数指定加载图片的过程中显示的图片，第三个参数指定加载图片失败的情况下显示的图片
        ImageLoader.ImageListener imageListener =
                ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
         imageLoader.get(mUrl,imageListener);

        return imageLoader;
    }*/
    public void getImageFromUrl(final String url) {
        // final Bitmap[] mBitmap = new Bitmap[1];
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null) {
            final String mUrl = url;
             ImageRequest imageRequest = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        public void onResponse(final Bitmap bitmap) {
                            putBitmap(mUrl,bitmap);
                            //imageView.setImageBitmap(bitmap);
                            //mBitmap[0] = bitmap;
                            new Thread(){
                                @Override
                                public void run() {
                                    Message message = handler.obtainMessage();
                                    message.obj = bitmap;
                                    message.sendToTarget();
                                    Log.i("tag","我运行了sendmessage!@");
                                }
                            }.start();
                        }
                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
            }
            );
            //Bitmap bitmap = mBitmap[0];
            requestQueue.add(imageRequest);
            //return bitmap;
        }
    }
    public Bitmap getBitmapFromCache(String url){
            return lruCache.get(url);
    }
    public void putBitmap(String url,Bitmap bitmap){
        lruCache.put(url,bitmap);
    }
}
