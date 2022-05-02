package com.example.music_news1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class HotList extends Activity {

    private Toolbar toolbar;
    private TextView textView3,textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotlist);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("爱乐——热榜");

        textView3=(TextView)findViewById(R.id.textview3);
        textView1=(TextView)findViewById(R.id.textview1);
        textView2=(TextView)findViewById(R.id.textview2);
        textView1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences sp=getSharedPreferences("user",MODE_PRIVATE);
                String user=sp.getString("username","");
                if(user.equals("")) {
                    Intent intent = new Intent(HotList.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(HotList.this, Follow.class);
                    startActivity(intent);
                }

            }
        });
        textView2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(HotList.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }


}