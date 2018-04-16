package com.example.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    String url = "https://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);//请求队列对象
        /** 解析http
         * url地址,服务器成功回调，服务器失败回调
         */
        StringRequest stringRequest = new StringRequest("https://www.baidu.com",
                new Response.Listener<String>(){

                    public void onResponse(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                        Log.i("aaa",s);

                    }
                },new Response.ErrorListener(){
                     public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this,volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
         );
        requestQueue.add(stringRequest);
        webView.getSettings().setJavaScriptEnabled(true);


        /**
         * 解析http，添加"GET"或"POST"方法
         */
   /* RequestQueue requestQueue1 = Volley.newRequestQueue(MainActivity.this);

    StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"https://www.baidu.com",
            new Response.Listener<String>(){

                public void onResponse(String s) {
                    Toast.makeText(MainActivity.this, "成功回调", Toast.LENGTH_SHORT).show();
                }
            },
            new Response.ErrorListener(){

                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
                }
            }) {

        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> map = new HashMap<String, String>();
            map.put("1","张三");
            map.put("2","李四");
            return map;
        }
    };
*/
    }
}
