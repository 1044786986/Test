package com.example.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by ljh on 2017/7/31.
 */

public class MyContentProvider extends ContentProvider {
    private final static int TEST = 100;
    private MySQLiteOpenHelper mOpenHelper;

    static UriMatcher buildUriMatcher(){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MyContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority,MyContract.PATH,TEST);
        return uriMatcher;
    }

    /**
     *在ContentProvider创建后被调用
     */
    public boolean onCreate() {
        mOpenHelper = new MySQLiteOpenHelper(getContext());
        return true;
    }

    /**
     * 根据uri查询出selection指定的条件所匹配的全部记录，并且可以指定查询哪些列，以什么方式排序
     * @param uri
     * @param strings   查询哪些列
     * @param s         查询条件
     * @param strings1
     * @param s1        排序方式
     * @return
     */
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (buildUriMatcher().match(uri)){
            case TEST:
                cursor = db.query(MyContract.TestEntry.TABLE_NAME,strings,s,strings1,s1,null,null);
                break;
        }
//        db.close();
        return cursor;
    }

    /**
     *返回当前uri的MIME类型,如果该URL对应的数据可能包括多条记录，那么MIME类型字符串就是以vnd.android.cursor.dir/开头
     * 如果只有一条记录，就是以vnd.android.cursor.item/开头
     * @param uri
     * @return
     */
    public String getType(Uri uri) {
        return null;
    }

    /**
     * 根据uri插入values对应的数据
     * @param uri
     * @param contentValues
     * @return
     */
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Uri returnUri;
        long id;
        switch (buildUriMatcher().match(uri)){
            case TEST:
                id = db.insert(MyContract.TestEntry.TABLE_NAME,null,contentValues);
                if(id > 0){
                    returnUri = MyContract.TestEntry.buildUri(id);
                }else{
                    throw new android.database.SQLException("UnKnowUri = " + uri);
                }
                break;
            default:
                throw new android.database.SQLException("UnKnowUri = " + uri);
        }
//        db.close();
        return returnUri;
    }

    /**
     * 根据uri删除s所指定的条件所匹配的全部记录
     * @param uri
     * @param s
     * @param strings
     * @return
     */
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    /**
     * 根据uri修改selection指定的所有条件所匹配的全部记录
     * @param uri
     * @param contentValues 新值
     * @param s   条件
     * @param strings   字段
     * @return
     */
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
