package com.example.music_news1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

public class quanzi extends Activity {

    private Toolbar toolbar;
    private ImageButton imageButton5,imageButton6,imageButton7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("爱乐——圈子");
        imageButton5=(ImageButton) findViewById(R.id.imageButton5);
        imageButton6=(ImageButton) findViewById(R.id.imageButton6);
        imageButton7=(ImageButton) findViewById(R.id.imageButton7);

        imageButton5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(quanzi.this, MainActivity2.class);
                startActivity(intent1);
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(quanzi.this, PersonActivity.class);
                startActivity(intent1);
            }
        });

    }


}
