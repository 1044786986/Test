package com.example.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ljh on 2017/7/31.
 */

public class Main2Activity extends Activity{
    private EditText etName;
    private EditText etPhone;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.etName = (EditText) findViewById(R.id.etName);
        this.etPhone = (EditText) findViewById(R.id.etPhone);
        this.button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

        ContentResolver contentResolver = getContentResolver();
        //向联系人中插入一条数据
        ContentValues values = new ContentValues();
        Uri uri = contentResolver.insert(RawContacts.CONTENT_URI,values);
        Long raw_contact_id = ContentUris.parseId(uri);
        values.clear();
        //插入人名
        values.put(StructuredName.RAW_CONTACT_ID,raw_contact_id);
        values.put(StructuredName.DISPLAY_NAME,name);
        values.put(StructuredName.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        uri = contentResolver.insert(ContactsContract.Data.CONTENT_URI,values);
        values.clear();
        //插入电话
        values.put(Phone.RAW_CONTACT_ID,raw_contact_id);
        values.put(Phone.NUMBER,phone);
        values.put(Phone.MIMETYPE,Phone.CONTENT_ITEM_TYPE);
        uri = contentResolver.insert(ContactsContract.Data.CONTENT_URI,values);
        values.clear();

                Toast.makeText(Main2Activity.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
