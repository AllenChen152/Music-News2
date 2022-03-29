package com.example.music_news1.tools;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

// BaseActivity不需要注册
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

