package com.example.music_news1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsHotList extends AppCompatActivity {
    private Toolbar toolbar;
    private SQLiteDatabase db;
    private ListView heat_ListView1;
    private ImageButton ib1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_hotlist);

        //新闻热度排序
        String database_path= getDatabasePath("UserStore.db").toString();
        db= SQLiteDatabase.openDatabase(database_path,null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        Cursor cursor=db.rawQuery("select * from news",null);
        int[] heat=new int[cursor.getCount()];
        String[] tit=new String[cursor.getCount()];
        String[] author=new String[cursor.getCount()];
        String[] date=new String[cursor.getCount()];
        int[] id=new int[cursor.getCount()];
        //将标题和热度取出来
        if(cursor.moveToFirst()){
            for(int j=0;j<cursor.getCount();j++){
                HashMap<String, Object> map = new HashMap<String, Object>();
                tit[j]=cursor.getString(2);
                heat[j]=cursor.getInt(4);
                author[j]=cursor.getString(1);
                date[j]=cursor.getString(3);
                id[j]=cursor.getInt(0);
                map.put("title",tit[j]);
                cursor.moveToNext();
            }
        }
        //冒泡排序
        for(int i=0;i<cursor.getCount()-1;i++){
            for(int j=0;j<cursor.getCount()-1-i;j++){
                if(heat[j]<heat[j+1]){
                    int h=heat[j];heat[j]=heat[j+1];heat[j+1]=h;
                    String t=tit[j];tit[j]=tit[j+1];tit[j+1]=t;
                    int i1=id[j];id[j]=id[j+1];id[j+1]=i1;
                    String a=author[j];author[j]=author[j+1];author[j+1]=a;
                    String d=date[j];date[j]=date[j+1];date[j+1]=d;
                }
            }
        }
        heat_ListView1=(ListView)findViewById(R.id.heat_ListView1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.newshotlist_items,tit);
        heat_ListView1.setAdapter(adapter);
        //点击listview其中一项，显示新闻内容
        heat_ListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(view.getContext(), NewsDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",id[i]);
                bundle.putString("title",tit[i]);
                bundle.putString("author",author[i]);
                bundle.putString("date",date[i]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        ib1=findViewById(R.id.ib1);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(NewsHotList.this, HotList.class);
                startActivity(intent1);
            }
        });
    }

    }
