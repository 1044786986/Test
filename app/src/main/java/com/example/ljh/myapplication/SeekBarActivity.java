package com.example.ljh.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by ljh on 2017/7/18.
 */

public class SeekBarActivity extends Activity implements android.widget.SeekBar.OnSeekBarChangeListener{
    private SeekBar seekBar;
    private TextView textView;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_seekbar);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        textView = (TextView) findViewById(R.id.seekbar_textview);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(100);
        seekBar.setProgress(0);

    }

    @Override   //数值改变
    public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean b) {
        textView.setText(i + "%");
    }

    @Override        //开始拖动
    public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

    }

    @Override   //停止拖动
    public void onStopTrackingTouch(android.widget.SeekBar seekBar) {

    }
}
