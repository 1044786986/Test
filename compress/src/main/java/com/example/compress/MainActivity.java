package com.example.compress;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final int CAMERA_CODE = 0;
    private final int ALUMB_CODE = 1;
    private final int permission_code[] = {CAMERA_CODE,ALUMB_CODE};
    private final String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int width;
    private int height;

    private Button btAlum,btCamera;
    private ImageView imageView;

    private Bitmap mBitmap;
    private Uri uri;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        checkPermission(permissions);
        btAlum = findViewById(R.id.btOpenAlum);
        btCamera = findViewById(R.id.btOpenCamera);
        imageView = findViewById(R.id.imageView);

        btAlum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(permissions[1],permission_code[1]);
            }
        });

        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkPermission(permissions[0],permission_code[0]);
                checkPermission(permissions);
                openCamera();
            }
        });

    }

    private void openCamera(){
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/camera");
            if(!file.exists()){
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),System.currentTimeMillis() + ".jpg");
            }else{
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera",
                        System.currentTimeMillis() + ".jpg");
            }
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this,
                        "com.example.ljh.myapplication.file_provider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            startActivityForResult(intent,CAMERA_CODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAlumb(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,ALUMB_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            width = imageView.getWidth();
            height = imageView.getHeight();
            Log.i("mainActivity","width = " + width + " " + height);
            switch (requestCode){
                case CAMERA_CODE:
//                    Bundle bundle = data.getExtras();
//                    mBitmap = (Bitmap) bundle.get("data");
//                    mBitmap = data.getParcelableExtra("data");
                    if (uri != null) {
                        mBitmap = CompressManager.compress(this, uri,width,height);
                        imageView.setImageBitmap(mBitmap);
                    }
                    break;
                case ALUMB_CODE:
                    try {
                        Uri uri = data.getData();
                        ParcelFileDescriptor parcelFileDescriptor =
                                getContentResolver().openFileDescriptor(uri,"r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        mBitmap = CompressManager.compress(fileDescriptor,width,height);
                        imageView.setImageBitmap(mBitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.e("mainActivity","File not found");
                        return;
                    }
                    break;
            }
        }
    }

    private void checkPermission(String[] permissions){
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permissions.length; i++) {
                if(checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{permissions[i]},permission_code[i]);
                }else{
                }
            }
        }
    }

    private void checkPermission(String permission,int permission_code){
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, permission_code);
        } else {
            switch (permission_code) {
                case ALUMB_CODE:
                    openAlumb();
                    break;
                case CAMERA_CODE:
                    openCamera();
                    break;
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }else{
            switch (requestCode){
                case CAMERA_CODE:
                    showPermissionTip("相机权限被禁用，请在设置中心");
                    break;
                case ALUMB_CODE:
                    showPermissionTip("读写权限被禁用，请在设置中心");
                    break;
            }
            return;
        }
    }

    

    private void toSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    private void showPermissionTip(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toSettings();
            }
        });
        builder.setNegativeButton("取消",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
