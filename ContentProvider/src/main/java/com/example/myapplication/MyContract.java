package com.example.myapplication;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ljh on 2018/4/23.
 */

public class MyContract {
    protected static final String CONTENT_AUTHORITY = "com.example.myapplication.MyContentProvider";
    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    protected static final String PATH = "test";

    public static final class TestEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        protected static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        protected static final String TABLE_NAME = "test";
        public static final String COLUMN_NAME = "name";
    }
}
