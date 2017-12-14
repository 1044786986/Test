package com.example.ljh.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Created by ljh on 2017/6/15.
 */

public class DatePikerActivity extends Activity {
    private TimePicker timepicker;
    private DatePicker datepicker;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @TargetApi(Build.VERSION_CODES.N)
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.datepiker);

        calendar = Calendar.getInstance(); //获取时间信息
        /*year = calendar.get(Calendar.YEAR); //获取年份
        month = calendar.get(Calendar.MONTH);//月份从0开始，需要+1
        day = calendar.get(Calendar.DAY_OF_MONTH);//
        hour = calendar.get(Calendar.HOUR_OF_DAY);//
        minute = calendar.get(Calendar.MINUTE);//
        setTitle(year + "-" + month + "-" + day + "-" + hour + "-" + minute);//创建标题*/

        datepicker = (DatePicker) findViewById(R.id.datePicker);//初始化组件
        timepicker = (TimePicker) findViewById(R.id.timePicker);

        //OnDateChangedListener
        datepicker.init(year, month, day, new DatePicker.OnDateChangedListener() { //日历选择器视图形式
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                setTitle(year + "-" + (month + 1) + "-" + day);

            }
        });
        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                setTitle(hourOfDay + ":" + minute);
            }
        });
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() { //日历选择器对话框形式
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                setTitle(year + "-" + (month + 1) + "-" + day);
            }
        }, 2000, calendar.get(Calendar.MONTH)+1, day).show();

     /*   new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                setTitle(hour +":"+ minute);
            }
        },int hour,int minute,true).show();*/
      /*  new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                setTitle(hour +":"+ minute);
            }
        }, hour, minute,true).show();
    }*/


    }
}