package com.example.music_news1;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

public class quanzi extends Activity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("爱乐——圈子");

    }


}
