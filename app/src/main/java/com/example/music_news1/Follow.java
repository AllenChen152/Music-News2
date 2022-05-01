package com.example.music_news1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Follow extends AppCompatActivity {

    private TextView textView3,textView1,textView2;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("爱乐——发现");

        textView3=(TextView)findViewById(R.id.textview3);
        textView1=(TextView)findViewById(R.id.textview1);
        textView2=(TextView)findViewById(R.id.textview2);
        textView3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(Follow.this, HotList.class);
                startActivity(intent1);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(Follow.this, MainActivity2.class);
                startActivity(intent1);
            }
        });
    }
}