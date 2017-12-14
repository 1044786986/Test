package com.example.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by ljh on 2017/7/31.
 */

public class MyContentProvider extends ContentProvider {
    /**
     *在ContentProvider创建后被调用
     */
    public boolean onCreate() {
        return false;
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
        return null;
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
        return null;
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
