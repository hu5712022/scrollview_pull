package com.god.hiahia.scrollview_pull;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.scrollview_pull.MySelfScrollView_s;

import utils.MyUtils;
import utils.VolleyHttp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final MySelfScrollView_s msv = (MySelfScrollView_s) findViewById(R.id.myScrollView);
        final View v = findViewById(R.id.rl_user_bg);
        msv.setAnimView(v);

        String url = "http://img3.duitang.com/uploads/item/201508/20/20150820135032_rHnYa.thumb.700_0.jpeg";
        VolleyHttp.HttpGetBitmap(this, url,
                new utils.VolleyHttp.doGetBitmapListener() {

                    @Override
                    public void doGet(Bitmap response) {
                        final ImageView iv_user_bg = (ImageView) findViewById(R.id.iv_user_bg);
                        response = MyUtils.fastblur(response, 10);
                        iv_user_bg.setImageBitmap(response);
                        msv.setInitHeight();
                    }
                }, null);

    }
}
