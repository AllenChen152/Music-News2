package com.example.music_news1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PersonActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton imageButton5,imageButton6,imageButton7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("爱乐——个人中心");
        imageButton5=(ImageButton) findViewById(R.id.imageButton5);
        imageButton6=(ImageButton) findViewById(R.id.imageButton6);
        imageButton7=(ImageButton) findViewById(R.id.imageButton7);

        imageButton5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(PersonActivity.this, MainActivity2.class);
                startActivity(intent1);
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(PersonActivity.this, quanzi.class);
                startActivity(intent1);
            }
        });

    }
}