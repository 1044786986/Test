package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

/**
 * Created by ljh on 2018/4/21.
 */

public class Fragment1 extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("aaa","fragment.onCreateView()");
        return inflater.inflate(R.layout.fragment1,container,false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("aaa","fragment.onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("aaa","fragment.onCreate()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("aaa","fragment.start()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("aaa","fragment.onStop()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("aaa","fragment.onResume()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("aaa","fragment.onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("aaa","fragment.onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("aaa","fragment.onDetach()");
    }
}
