package com.example.myapplication.test;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myapplication.MySQLiteOpenHelper;

/**
 * Created by ljh on 2018/4/23.
 */

public class MyContentProvider extends ContentProvider{
    private static final int TABLE1_CODE = 1;
    private MySQLiteOpenHelper mOpenHelper;

    public static UriMatcher buildUriMatcher(){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        final String authority = MyContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(MyContract.CONTENT_AUTHORITY,MyContract.PATH,TABLE1_CODE);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MySQLiteOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        switch(buildUriMatcher().match(uri)){
            case TABLE1_CODE:
                cursor = db.query(MyContract.MyEntry.TABLE_NAME,projection,selection,selectionArgs,sortOrder,null,null);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Uri returnUri = null;

        switch (buildUriMatcher().match(uri)){
            case TABLE1_CODE:
                long id = db.insert(MyContract.MyEntry.TABLE_NAME,null,values);
                if(id > 0){
                    returnUri = MyContract.MyEntry.buildUri(id);
                }else{

                }
                break;
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
