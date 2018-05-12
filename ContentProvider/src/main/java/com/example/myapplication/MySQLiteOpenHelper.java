package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ljh on 2018/4/23.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    private static final String NAME = "ljh.db";
    private static final int VERSION = 1;

    public MySQLiteOpenHelper(Context context){
        super(context,NAME,null,VERSION);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CONTENT_TABLE =  "create table " + MyContract.TestEntry.TABLE_NAME + "("
                + MyContract.TestEntry._ID + " TEXT PRIMARY KEY," + MyContract.TestEntry.COLUMN_NAME +
                " TEXT NOT NULL)";
        db.execSQL(SQL_CREATE_CONTENT_TABLE);
        Log.d("aaa","OpenHelper.create()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists " + MyContract.TestEntry.TABLE_NAME);
        if(oldVersion == 1 && newVersion == 2){

        }
    }


}
