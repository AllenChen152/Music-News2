package com.example.music_news1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Follow extends AppCompatActivity {

    private TextView textView3,textView1,textView2;
    private Toolbar toolbar;
    private ImageView guanzhu1,guanzhu2,guanzhu3,guanzhu4,guanzhu5,guanzhu6,guanzhu7,guanzhu8;
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
                Intent intent1 = new Intent(Follow.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        final int[] i = {0};
        guanzhu1=(ImageView) findViewById(R.id.guanzhu1);
        guanzhu2=(ImageView) findViewById(R.id.guanzhu2);
        guanzhu3=(ImageView) findViewById(R.id.guanzhu3);
        guanzhu4=(ImageView) findViewById(R.id.guanzhu4);
        guanzhu5=(ImageView) findViewById(R.id.guanzhu5);
        guanzhu6=(ImageView) findViewById(R.id.guanzhu6);
        guanzhu7=(ImageView) findViewById(R.id.guanzhu7);
        guanzhu8=(ImageView) findViewById(R.id.guanzhu8);

        guanzhu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(i[0] == 0);
                if(i[0] ==0) {
                    i[0] = 1;
                    Toast.makeText(Follow.this, "已取消关注！！", Toast.LENGTH_SHORT).show();
                }
                else {
                    i[0] = 0;
                    Toast.makeText(Follow.this, "已关注！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        guanzhu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(i[0] == 0);
                if(i[0] ==0) {
                    i[0] = 1;
                    Toast.makeText(Follow.this, "已取消关注！！", Toast.LENGTH_SHORT).show();
                }
                else {
                    i[0] = 0;
                    Toast.makeText(Follow.this, "已关注！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        guanzhu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(i[0] == 0);
                if(i[0] ==0) {
                    i[0] = 1;
                    Toast.makeText(Follow.this, "已取消关注！！", Toast.LENGTH_SHORT).show();
                }
                else {
                    i[0] = 0;
                    Toast.makeText(Follow.this, "已关注！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        guanzhu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(i[0] == 0);
                if(i[0] ==0) {
                    i[0] = 1;
                    Toast.makeText(Follow.this, "已取消关注！！", Toast.LENGTH_SHORT).show();
                }
                else {
                    i[0] = 0;
                    Toast.makeText(Follow.this, "已关注！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        guanzhu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(i[0] == 0);
                if(i[0] ==0) {
                    i[0] = 1;
                    Toast.makeText(Follow.this, "已取消关注！！", Toast.LENGTH_SHORT).show();
                }
                else {
                    i[0] = 0;
                    Toast.makeText(Follow.this, "已关注！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        guanzhu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(i[0] == 0);
                if(i[0] ==0) {
                    i[0] = 1;
                    Toast.makeText(Follow.this, "已取消关注！！", Toast.LENGTH_SHORT).show();
                }
                else {
                    i[0] = 0;
                    Toast.makeText(Follow.this, "已关注！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        guanzhu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(i[0] == 0);
                if(i[0] ==0) {
                    i[0] = 1;
                    Toast.makeText(Follow.this, "已取消关注！！", Toast.LENGTH_SHORT).show();
                }
                else {
                    i[0] = 0;
                    Toast.makeText(Follow.this, "已关注！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        guanzhu8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(i[0] == 0);
                if(i[0] ==0) {
                    i[0] = 1;
                    Toast.makeText(Follow.this, "已取消关注！！", Toast.LENGTH_SHORT).show();
                }
                else {
                    i[0] = 0;
                    Toast.makeText(Follow.this, "已关注！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}