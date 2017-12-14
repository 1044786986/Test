package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;

public class MainActivity extends Activity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);

        if(hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }*/

        ContentResolver contentResolver = getContentResolver();
        //根据联系人id查询号码
        Cursor cursor = contentResolver.query(Contacts.CONTENT_URI,new String[]{Contacts._ID,Contacts.DISPLAY_NAME},null,null,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(Contacts._ID));
               // String displayname = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
                Log.i("infoo","id:" + id);
                Log.i("infoo","name:" + cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME)));

                Cursor cursor1 = contentResolver.query(Phone.CONTENT_URI,new String[]{Phone.NUMBER,Phone.TYPE},
                        Phone.CONTACT_ID + "=" + id,null,null);
                if(cursor1 != null){
                    while(cursor1.moveToNext()){
                       // int number = cursor1.getInt(cursor1.getColumnIndex(Phone.NUMBER));
                        int type = cursor1.getInt(cursor1.getColumnIndex(Phone.TYPE));

                        if(type == Phone.TYPE_HOME){
                            Log.i("infoo","家庭电话:" + cursor1.getString(cursor1.getColumnIndex(Phone.NUMBER)));
                        }else if(type == Phone.TYPE_MOBILE){
                            Log.i("infoo","手机电话:" + cursor1.getString(cursor1.getColumnIndex(Phone.NUMBER)));
                        }else{
                            Log.i("infoo",cursor1.getString(cursor1.getColumnIndex(Phone.NUMBER)));
                        }
                    }
                    cursor1.close();
                }
                Cursor cursor2 = contentResolver.query(Email.CONTENT_URI,new String[]{Email.TYPE,Email.DATA},
                        Email._ID + "=" + id,null,null);
                if(cursor2 != null){
                    while (cursor2.moveToNext()){
                        int type = cursor2.getInt(cursor2.getColumnIndex(Email.TYPE));
                        //String data = cursor2.getString(cursor2.getColumnIndex(Email.DATA));

                        if(type == Email.TYPE_WORK){
                            Log.i("infoo","工作邮箱:" + cursor2.getString(cursor2.getColumnIndex(Email.DATA)));
                        }else{
                            Log.i("infoo","其他邮箱:" + cursor2.getString(cursor2.getColumnIndex(Email.DATA)));
                        }
                    }
                    cursor2.close();
                }
            }
            cursor.close();
        }

    }
}
