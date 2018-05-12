package com.example.loadimagecache;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ljh on 2018/4/25.
 */

public class SaveImageInSystem extends AppCompatActivity{
    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"images";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkDir();
        saveImage();
//        updateAlbum();
    }

    private void checkDir(){
        File file = new File(BASE_PATH);
        if(!file.exists()) {
            file.mkdir();
        }
    }

    private void saveImage(){
        /**
         * 位置不确定
         */
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.a);
//        MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"a","neuSoft");

        /**
         * 指定位置
         */
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.a);
            String filename = System.currentTimeMillis() + ".jpg";
            File file = new File(BASE_PATH,filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            bitmap.recycle();

            Uri uri = Uri.fromFile(file);
            updateAlbum(uri);

            if(isSuccess){
                Log.d("aaa","success");
            }else{
                Log.d("aaa","failed");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAlbum(Uri uri){
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
//                Uri.fromFile(new File()));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri);
            sendBroadcast(intent);
        }else{
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED ,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())
            ));
        }
    }
}
