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
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
//        LoadImage();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LoadImage();
//            }
//        }).start();
//        LoadImageRxJava();

        Observable observable = Observable.just(1,2,3);
        observable.lift(new Observable.Operator<Integer,String>(){

            @Override
            public Subscriber<? super String> call(Subscriber<? super Integer> subscriber) {
                return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i("aaa","onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("aaa","s = " + s);
                    }
                };
            }
        });
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
        observable.subscribe(subscriber1);
    }

    /**
     * 普通写法
     */
    private void LoadImage(){
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                +"/Screenshots");
        File[] files= folder.listFiles();
//        int i=0;
        for (File f : files){
//            i++;
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
//            if(i > 10){
//                return;
//            }
            try {
                FileInputStream fileInputStream = new FileInputStream(f);
                bitmap = BitmapFactory.decodeStream(fileInputStream);
                handler.post(runnable);
                Thread.sleep(500);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用RxJava
     */
    private void LoadImageRxJava(){
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        + "/Screenshots");
        File[] files = file.listFiles();
//        int i=0;
        Observable.from(files)
//                .flatMap(new Func1<File, Observable<File>>() {
//                    @Override
//                    public Observable<File> call(File file) {
//                        return Observable.from(file.listFiles());
//                    }
//                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        FileInputStream fileInputStream = null;
                        try {
                             fileInputStream = new FileInputStream(file);
                             Thread.sleep(500);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return BitmapFactory.decodeStream(fileInputStream);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>(){
                    @Override
                    public void call(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {
            Log.i("aaa","observer.onCompleted()");
        }

        @Override
        public void onError(Throwable e) {
            Log.i("aaa","observer.onError() = " + e);
        }

        @Override
        public void onNext(String s) {
            Log.i("aaa","observer.onNext() = " + s);
        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.i("aaa","subscriber.onCompleted()");
        }

        @Override
        public void onError(Throwable e) {
            Log.i("aaa","subscriber.onError() = " + e);
        }

        @Override
        public void onNext(String s) {
            Log.i("aaa","subscriber.onNext() = " + s);
        }
    };

//    /**
//     * compose
//     */
//    public class LiftAllTransformer implements Observable.Transformer<Integer,String>{
//
//        @Override
//        public Observable<String> call(Observable<Integer> integerObservable) {
//            return integerObservable.lift();
//        }
//    }
}
