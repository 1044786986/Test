package com.example.ljh.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


/**
 * Created by ljh on 2017/7/12.
 */

public class WebViewActivity extends Activity{
    //private String url = "http://2014.qq.com/";
    private String url = "https://www.baidu.com/";
    private WebView webView;
    private ProgressDialog dialog;
    private ProgressBar progressBar;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
       // requestWindowFeature(Window.FEATURE_PROGRESS);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_webview);

        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.webview_progressBar);
        //webview加载本地资源
        //webView.loadUrl("file///android_asset/example.html");
        //加载web资源
        webView.loadUrl(url);
        //使网页在webview中打开
        webView.setWebViewClient(new WebViewClient());
        //启用支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        //webview优先使用缓存加载
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress 1-100间的整数
                if(newProgress == 100){
                    CloseDialog();//加载完毕，关闭进度条
                }else{
                    OpenDialog(newProgress);//打开进度条
                }
                super.onProgressChanged(view, newProgress);
            }


        });

    }

    private void OpenDialog(int newProgress) {
       /* if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setProgress(newProgress);
            dialog.setTitle("正在加载");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(true);
            dialog.show();

        }else{
            dialog.setProgress(newProgress);
        }*/
        if(progressBar == null || progressBar.getProgress() == 0){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
        }else{
            progressBar.setProgress(newProgress);
        }
    }

    private void CloseDialog() {
        /*if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }*/
        if(progressBar.getProgress()!=0 && progressBar.isShown()){
            progressBar.setProgress(0);
            progressBar.setVisibility(View.INVISIBLE);

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();//返回上一页面
                return true;
            }else{
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
