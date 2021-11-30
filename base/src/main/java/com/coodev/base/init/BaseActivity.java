package com.coodev.base.init;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.coodev.base.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}