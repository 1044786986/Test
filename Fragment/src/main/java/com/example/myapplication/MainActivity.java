package com.example.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button fragment_btn1;
    private Button fragment_btn2;
    private Button remove_btn;
    private Button replace_btn;
    private Button hide1,hide2,show1,show2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_btn1 = (Button) findViewById(R.id.fragment_btn1);
        fragment_btn2 = (Button) findViewById(R.id.fragment_btn2);
        remove_btn = (Button) findViewById(R.id.remove_btn);
        replace_btn = (Button) findViewById(R.id.replace_btn);
        hide1 = (Button) findViewById(R.id.fragment_hide1);
        hide2 = (Button) findViewById(R.id.fragment_hide2);
        show1 = (Button) findViewById(R.id.fragment_show);
        show2 = (Button) findViewById(R.id.fragment_show2);
        fragment_btn1.setOnClickListener(this);
        fragment_btn2.setOnClickListener(this);
        remove_btn.setOnClickListener(this);
        replace_btn.setOnClickListener(this);
        hide1.setOnClickListener(this);
        hide2.setOnClickListener(this);
        show1.setOnClickListener(this);
        show2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_btn1 :
                Toast.makeText(this,"fragment_btn1",Toast.LENGTH_SHORT).show();
                /*Fragment_title_Activity fragment1 = new Fragment_title_Activity();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction begintransaction = fm.beginTransaction();
                begintransaction.add(R.id.framelayout,fragment1);
                begintransaction.addToBackStack(null);
                begintransaction.commit();*/
                Fragment_title_Activity fragment1 = new Fragment_title_Activity();
                addFragment(fragment1,"fragment1");
                break;
            case R.id.fragment_btn2:
                Toast.makeText(this,"fragment_btn2",Toast.LENGTH_SHORT).show();
              /*  Fragment_content fragment2 = new Fragment_content();
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction transaction = fm2.beginTransaction();
                transaction.add(R.id.framelayout,fragment2);
                transaction.addToBackStack(null);
                transaction.commit();*/
                Fragment_content fragment2 = new Fragment_content();
                addFragment(fragment2,"fragment2");
                break;
            case R.id.remove_btn :
                removeFragment("fragment1");
                break;
            case R.id.replace_btn:
                replaceFragment("fragment1");
                break;
            case R.id.fragment_hide1:
                hideFragment("fragment1");
                break;
            case R.id.fragment_hide2:
                hideFragment("fragment2");
                break;
            case R.id.fragment_show:
                //showFragment("fragment1");
                Fragment_title_Activity fragment_title = new Fragment_title_Activity();
                Fragment_content fragment_content = new Fragment_content();
                switchContent(fragment_content,fragment_title,"fragment1");
                break;
            case R.id.fragment_show2:
                //showFragment("fragment2");
                Fragment_title_Activity fragment_title1 = new Fragment_title_Activity();
                Fragment_content fragment_content1 = new Fragment_content();
                switchContent(fragment_title1,fragment_content1,"fragment2");
                break;
        }
    }
    //增加fragment
    private void addFragment(Fragment fragment, String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.framelayout,fragment,tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //移除fragment
    private void removeFragment(String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(tag);
        transaction.remove(fragment);
        transaction.commit();
    }
    //替换fragment
    private void replaceFragment(String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(tag);
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }
    //隐藏fragment
    private void hideFragment(String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(tag);
        transaction.hide(fragment);
        transaction.commit();
    }
    //显示已隐藏的fragment
    private void showFragment(String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(tag);
        transaction.show(fragment);
        transaction.commit();
    }
    //实战中切换fragment页面
    private void switchContent(Fragment from,Fragment to,String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if(!to.isAdded()){
            transaction.hide(from).add(R.id.framelayout,to,tag).commit();

        }else{
            transaction.hide(from).show(to).commit();
        }
    }

}
