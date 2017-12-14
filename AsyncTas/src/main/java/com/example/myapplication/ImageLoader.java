package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ljh on 2017/8/15.
 */

public class ImageLoader{
    private Handler handler = new Handler();
    private ImageView mImageView;
    private String mUrl;
    private Bitmap bitmap;
    private LruCache<String,Bitmap> lruCache;
    private ListView listView;
    private Set<mAsyncTask> SetAsyncTask;

    ImageLoader(ListView listView){
        this.listView = listView;
        SetAsyncTask = new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        lruCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    //用来加载从start到end的所有图片
    public void loadImages(int start,int end){
        for(int i=start;i<end;i++){
            String url = MyBaseAdapter.urls[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if(bitmap == null){
                mAsyncTask task = new mAsyncTask(url);
                task.execute(url);
                SetAsyncTask.add(task);
            }else{
                Log.i("tag", String.valueOf(lruCache.size()));

                ImageView imageView = (ImageView) listView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);

            }
        }
    }

    public void cancel(){
        if(SetAsyncTask != null){
            for(mAsyncTask i : SetAsyncTask){
                i.cancel(false);
            }
        }
    }

    //图片增加到缓存中
    public void addBitmapToCache(String url,Bitmap bitmap){
        if(getBitmapFromCache(url) == null){
            lruCache.put(url,bitmap);
        }
    }
    //缓存中获取图片
    public Bitmap getBitmapFromCache(String url){
        return lruCache.get(url);
    }

    public void showImageByThread( ImageView imageView, String url){
        mImageView = imageView;
        mUrl = url;
        new Thread(){

            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                     bitmap = getBitmapFromUrl(mUrl);

                    handler.post(new Runnable() {

                        public void run() {
                           if(mImageView.getTag().equals(mUrl)){
                                mImageView.setImageBitmap(bitmap);
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void showImageByAsyncTask(ImageView imageView,String url){
        mImageView = imageView;

        if(getBitmapFromCache(url) == null){
            /*mAsyncTask m = new mAsyncTask(url);
            m.execute(url);*/
            mImageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            Bitmap bitmap = getBitmapFromCache(url);
            mImageView.setImageBitmap(bitmap);
        }

        Log.i("tag","每次都会运行了showImageByAsyncTask");
    }
    class mAsyncTask extends AsyncTask<String,Void,Bitmap>{
        //private ImageView imageView;
        private String url;

        mAsyncTask(String url){
           // this.imageView = imageView;
            this.url = url;
        }

        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = getBitmapFromUrl(url);
            if(bitmap != null){
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) listView.findViewWithTag(url);
            if(imageView != null && bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
            SetAsyncTask.remove(this);
        }
    }
    private Bitmap getBitmapFromUrl(String url){
        Bitmap bitmap = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            inputStream.close();
            bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return bitmap;
    }


}
