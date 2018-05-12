package com.example.rxjava;

/**
 * Created by ljh on 2018/4/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxJava2 extends AppCompatActivity{
    private ImageView imageView;
    private Subscription mSubscription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubscription.request(Long.MAX_VALUE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        showImage();
        showImageFlowable();
//        Flowable();
    }

    private void showImage(){
        /**
         * 普通模式
         */
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                for (int i = 0;; i++) {
//                    e.onNext(i);
//                    Log.d("aaa", "e.onNext = " + i);
//                }
//            }
//        }).subscribeOn(Schedulers.io())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d("aaa","subscriber.onNext = " + integer);
//                    }
//                });

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                + "");
        File files[] = file.listFiles();
        for(File f : files){
            Log.d("aaa","f = " + f);
        }
        Observable.fromArray(files)
                .flatMap(new Function<File, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(File file) throws Exception {
                        return Observable.fromArray(file.listFiles());
                    }
                })
                .filter(new Predicate<File>() {
                    @Override
                    public boolean test(File file) throws Exception {
                        if(file.getName().endsWith(".PNG") || file.getName().endsWith(".png")){
                            Log.d("aaa",file.getName());
                        }
                        return file.getName().endsWith(".PNG") || file.getName().endsWith(".png");
                    }
                })
                .map(new Function<File, Bitmap>() {
                    public Bitmap apply(File file)  {
                        FileInputStream fileInputStream = null;
                        try {
                            fileInputStream = new FileInputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        return BitmapFactory.decodeStream(fileInputStream);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Bitmap value) {
                        Log.d("aaa","onNext()");
                        imageView.setImageBitmap(value);
//                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("aaa","onError() = " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * flowable解决阻塞
     * BackPressureStrategy.Mode:
     * MISSING:如果流的速度无法保持同步，可能会抛出MissingBackpressureException或IllegalStateException
     * BUFFER:上游不断地发出onNext()请求，直到下游处理完，也就是和Observable一样，缓存池无限大，最后直到程序崩溃
     * ERROR:下游跟不上上游速度时抛出MissingBackpressureException
     * DROP:下游跟不上速度时把onNext()的值抛弃
     * LATEST:一直保留最新的onNext()的值，直到被下游消费掉
     */
    public void Flowable(){

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for (int i=0;i<140;i++){
//                    Log.d("aaa","e.onNext() = " + i);
                    e.onNext(i);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.computation())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("aaa","onNext() = " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("aaa","onError  = " + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("aaa","onComplete");
                    }
                });
    }

    public void showImageFlowable(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+
                "/Screenshots");
        File[] files = file.listFiles();
        for(File f : files){
            Log.d("aaa","f = " + f.getName());
        }
        Flowable.fromArray(files)
                .filter(new Predicate<File>() {
                    @Override
                    public boolean test(File file) throws Exception {
                        return file.getName().endsWith(".png") || file.getName().endsWith(".PNG");
                    }
                })
                .map(new Function<File, Bitmap>() {
                    @Override
                    public Bitmap apply(File file) throws Exception {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                        fileInputStream.close();
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if(bitmap != null){
                            imageView.setImageBitmap(bitmap);
                        }
                        else{
                            Log.d("aaa","bitmap == null");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("aaa","Error = " + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("aaa","onComplete()");
                    }
                });
    }
}
