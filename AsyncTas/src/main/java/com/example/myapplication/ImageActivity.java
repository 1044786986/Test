package com.example.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ljh on 2017/7/24.
 */

public class ImageActivity extends Activity{
    private ProgressBar progressBar;
    private ImageView imageView;
    private static String url = "http://pic3.zhongsou.com/image/38063b6d7defc892894.jpg";
    private MyAsyncTask myAsyncTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

         myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);//通过调用execute方法开始处理异步任务.相当于线程中的start方法.
        imageView = (ImageView) findViewById(R.id.image);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
    ImageActivity(String url){
        this.url = url;
    }
    /**
     *Params:启动任务时输入的参数类型.
     *Progress:后台任务执行中返回进度值的类型.
     *Result:后台任务执行完成后返回结果的类型.
     */
    class MyAsyncTask extends AsyncTask<String,Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) { //必须重写,异步执行后台线程要完成的任务,
                                                            // 耗时操作将在此方法中完成.
            String url = strings[0];
            Bitmap bitmap = null;
            URLConnection urlConnection;//定义网络连接对象
            InputStream inputStream;//用于获取数据的输入流

            for(int i=0;i<100;i++) {
                publishProgress(i);
                if (myAsyncTask.isCancelled()) {
                    break;
                }
            }
            try {
                Thread.sleep(3000);
                    //调用publishProgress方法将自动触发onProgressUpdate方法来进行进度条的更新.


                /**
                 * 以下为固定代码
                 */
                urlConnection = new URL(url).openConnection();//获取网络连接对象
                inputStream = urlConnection.getInputStream();//获取输入流
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);//通过decodeStreanm解析输入流,将输入流解析成bitmap
                inputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return bitmap;//讲bitmap作为返回值
        }

        @Override
        protected void onPreExecute() { //执行后台耗时操作前被调用,通常用于进行初始化操作.
            super.onPreExecute();

        }

        @Override
        //:当doInBackground方法完成后,系统将自动调用此方法,并将doInBackground方法返回的值传入此方法.通过此方法进行UI的更新
        protected void onPostExecute(Bitmap bitmap) {
            Log.i("tagggggg","bitmap");
            imageView.setImageBitmap(bitmap);
            if(myAsyncTask.isCancelled()){
                return;
            }
        }

        @Override
        //当在doInBackground方法中调用publishProgress方法更新任务执行进度后,将调用此方法.通过此方法我们可以知晓任务的完成进度.
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //通过publishProgress方法传过来的值进行进度条的更新.
            progressBar.setProgress(values[0]);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
            //cancel方法只是将对应的AsyncTask标记为cancelt状态,并不是真正的取消线程的执行.
            myAsyncTask.cancel(true);
        }
    }
}
