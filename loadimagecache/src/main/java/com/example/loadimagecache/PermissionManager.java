package com.example.loadimagecache;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ljh on 2018/4/25.
 */

public class PermissionManager extends AppCompatActivity implements IPermissionCallBack {
    private static final PermissionManager mPermissionManager = new PermissionManager();
    public static final int WRITE_EXTERNAL_STORAGE = 0;
    public static final int CAMERA = 1;
    public static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    public static final int[] PERMISSION_CODES = {WRITE_EXTERNAL_STORAGE,CAMERA};
    public Context context;

    public PermissionManager(){}

    @RequiresApi(api = Build.VERSION_CODES.M)
    public synchronized void applyPermission(Context context, String[] permissions){
        if(context == null || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return;
        }

        for(String s : permissions){
            if(ContextCompat.checkSelfPermission(this,s) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{s},WRITE_EXTERNAL_STORAGE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void success() {

    }

    @Override
    public void failed(int permissionCode) {

    }
}
