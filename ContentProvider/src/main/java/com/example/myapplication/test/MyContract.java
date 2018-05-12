package com.example.myapplication.test;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ljh on 2018/4/23.
 */

public class MyContract {
    protected  static final String CONTENT_AUTHORITY = "com.example.myapplication.test.MyContentProvider";
    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    protected static final String PATH = "table1";

    public static class MyEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String TABLE_NAME = "table1";
        public static final String COLUMN_NAME = "username";

        public static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }
}
