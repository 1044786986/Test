package com.example.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class MainActivity extends Activity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queryMyContentProvider();
    }

    private void queryMyContentProvider(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyContract.TestEntry.COLUMN_NAME,"a");
        contentValues.put(MyContract.TestEntry._ID,System.currentTimeMillis());
        getContentResolver().insert(MyContract.TestEntry.CONTENT_URI,contentValues);

        Cursor cursor = getContentResolver().query(MyContract.TestEntry.CONTENT_URI,null,null,null,null);
        try{
            Log.d("aaa","count = " + cursor.getCount());
            cursor.moveToFirst();
            Log.d("aaa","first = " + cursor.getString(1));
        }finally{
            cursor.close();
        }
    }

    private void queryPhone(){
        /*  int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);

        if(hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }*/

        ContentResolver contentResolver = getContentResolver();
        //根据联系人id查询号码
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},null,null,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                // String displayname = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
                Log.i("infoo","id:" + id);
                Log.i("infoo","name:" + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                Cursor cursor1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,null,null);
                if(cursor1 != null){
                    while(cursor1.moveToNext()){
                        // int number = cursor1.getInt(cursor1.getColumnIndex(Phone.NUMBER));
                        int type = cursor1.getInt(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

                        if(type == ContactsContract.CommonDataKinds.Phone.TYPE_HOME){
                            Log.i("infoo","家庭电话:" + cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }else if(type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE){
                            Log.i("infoo","手机电话:" + cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }else{
                            Log.i("infoo",cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }
                    }
                    cursor1.close();
                }
                Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,new String[]{ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.DATA},
                        ContactsContract.CommonDataKinds.Email._ID + "=" + id,null,null);
                if(cursor2 != null){
                    while (cursor2.moveToNext()){
                        int type = cursor2.getInt(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        //String data = cursor2.getString(cursor2.getColumnIndex(Email.DATA));

                        if(type == ContactsContract.CommonDataKinds.Email.TYPE_WORK){
                            Log.i("infoo","工作邮箱:" + cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                        }else{
                            Log.i("infoo","其他邮箱:" + cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                        }
                    }
                    cursor2.close();
                }
            }
            cursor.close();
        }
    }
}
