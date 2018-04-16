package com.example.compress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ljh on 2018/3/30.
 */

public class LoadBigImageActivity extends AppCompatActivity{
    BigImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_load_big_image);
        imageView = findViewById(R.id.imageView);
        try {
            InputStream inputStream = getAssets().open("world.jpg");
            imageView.setInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
