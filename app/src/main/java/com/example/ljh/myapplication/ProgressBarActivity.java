package com.example.ljh.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ljh on 2017/7/11.
 */

public class ProgressBarActivity extends Activity implements View.OnClickListener {
    private ProgressBar progressbar;
    private ProgressDialog progressDialog;
    private Button button_show;
    private Button add;
    private Button reduce;
    private Button teset;
    private TextView textview;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        //启用窗口特征
      //  requestWindowFeature(Window.FEATURE_PROGRESS);
      //  requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_progressbar);
        init();


    }
    private void init(){
        progressbar = (ProgressBar) findViewById(R.id.progressBar_horiz);
        add = (Button) findViewById(R.id.progressbar_add);
        reduce = (Button) findViewById(R.id.progressbar_reduce);
        teset = (Button) findViewById(R.id.progressbar_reset);
        textview = (TextView) findViewById(R.id.progressbar_text);
        button_show = (Button) findViewById(R.id.progressbar_button);
        button_show.setOnClickListener(this);
        add.setOnClickListener(this);
        reduce.setOnClickListener(this);
        teset.setOnClickListener(this);
        textview.setOnClickListener(this);
        //获取第一进度条的进度
        int first = progressbar.getProgress();
        //获取第二进度条的进度
        int second = progressbar.getSecondaryProgress();
        int max = progressbar.getMax();
        textview.setText("第一进度条进度:" + (int)(first/(float)max*100) +"%" +
            "第二进度条进度:" + (int)(second/(float)max*100) + "%");
    }
    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.progressbar_add:
            progressbar.incrementProgressBy(10);
            progressbar.incrementSecondaryProgressBy(10);
               break;
           case R.id.progressbar_reduce:
            progressbar.incrementProgressBy(-10);
            progressbar.incrementSecondaryProgressBy(-10);
               break;
           case R.id.progressbar_reset:
            progressbar.setProgress(0);
            progressbar.setSecondaryProgress(0);
               break;
           case R.id.progressbar_button:
            //新建progressDialog对象
               progressDialog = new ProgressDialog(this);
               progressDialog.setMax(100);
               progressDialog.incrementProgressBy(50);
               //是否明确显示进度
               progressDialog.setIndeterminate(false);
               //设置风格
               progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
               //设置标题
               progressDialog.setTitle("英雄联盟");
               //设置对话框里的文字内容
               progressDialog.setMessage("欢迎来到英雄联盟");
               //设置图标
               progressDialog.setIcon(R.mipmap.ic_launcher);
               //点击弹出框之外的地方可以退出弹出框
               progressDialog.setCancelable(true);
               //设定一个确定按钮
               progressDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       Toast.makeText(ProgressBarActivity.this,"欢迎来到英雄联盟",Toast.LENGTH_SHORT).show();
                   }
               });
               progressDialog.show();
               break;
       }
        textview.setText("第一进度条进度:" + (int)(progressbar.getProgress()/(float)progressbar.getMax()*100)+"%"
        + (int)(progressbar.getSecondaryProgress()/(float)progressbar.getMax()*100) + "%");
    }

}
