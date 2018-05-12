package com.example.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJava1 extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap bitmap;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            imageView.setImageBitmap(bitmap);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava1);
        imageView = findViewById(R.id.imageView);
//        LoadImage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadImageRxJava();
        Subscriber subscriber1 = new Subscriber() {
            @Override
            public void onCompleted() {
                Log.i("aaa","subscriber1.onCompleted()");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.i("aaa","subscriber.onNext() = " + o);
            }
        };
//        Observable.just(1,2,3).subscribe(subscriber1);
//        Observable.create(new Observable.OnSubscribe<Integer>(){
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                Log.d("aaa","Observable.onNext()1");
//                subscriber.onNext(1);
//                Log.d("aaa","Observable.onNext()2");
//                subscriber.onNext(2);
//                Log.d("aaa","Observable.onNext()3");
//                subscriber.onNext(3);
//            }
//        }).subscribe(subscriber1);
    }

    /**
     * 使用RxJava
     */
    private void LoadImageRxJava(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                +"/");
        File[] files = file.listFiles();
//        File[] files1 = files[0].listFiles();
//        for(File f : files1){
//            Log.d("aaa","fileName = " + f.getName());
//        }
        Observable.from(files)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
//                .filter(new Func1<File, Boolean>() {
//                    @Override
//                    public Boolean call(File file) {
//                        if(!file.getName().endsWith(".PNG") || !file.getName().endsWith(".png")){
//                            Log.d("aaa","file.getName() = " + file.getName());
//                        }
//                        return file.getName().endsWith(".JPG") || file.getName().endsWith(".jpg");
//                    }
//                })
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        FileInputStream fileInputStream = null;
                        try {
                             fileInputStream = new FileInputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        return BitmapFactory.decodeStream(fileInputStream);
//                        return CompressManager.comPressFromBitmap(
//                                BitmapFactory.decodeStream(fileInputStream),imageView.getWidth(),imageView.getHeight());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        Log.d("aaa","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("aaa","onError = " + e);
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if(bitmap != null){
                            Log.d("aaa","bitmap != null");
                            imageView.setImageBitmap(bitmap);
                        }else{
                            Log.d("aaa","bitmap == null");
                        }
                    }
                });
    }
}
