package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ljh on 2017/8/2.
 */

public class SystemServiceActivity extends Activity{
    WifiManager wifiManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = (LayoutInflater) SystemServiceActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_sservice,null);
        setContentView(view);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                if(isNextWorkConnected(SystemServiceActivity.this) == true){
                    Toast.makeText(SystemServiceActivity.this,"网络已连接",Toast.LENGTH_SHORT).show();
               }
                     wifiManager = (WifiManager) SystemServiceActivity.this.getSystemService(WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(SystemServiceActivity.this,"wifi已打开",Toast.LENGTH_SHORT).show();
               // }
                break;
            case R.id.button2:
                wifiManager = (WifiManager) SystemServiceActivity.this.getSystemService(WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);
                Toast.makeText(SystemServiceActivity.this,"wifi已关闭",Toast.LENGTH_SHORT).show();
                break;
        }
    }
        public boolean isNextWorkConnected(Context context){
            if(context != null){
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();//获取网络信息

                if(networkInfo != null){//如果网络状态不为空，说明网络状态是存在的
                    return networkInfo.isAvailable();
                }
            }
            return  false;

    }

    }


