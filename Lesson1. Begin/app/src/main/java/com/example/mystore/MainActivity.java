package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://vpd121.itstep.click/images/1.jpg";

        ImageView ivAvater = (ImageView) findViewById(R.id.ivAvater);

        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().override(600))
                .into(ivAvater);

    }
}