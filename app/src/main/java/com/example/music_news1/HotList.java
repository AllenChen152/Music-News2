package com.example.music_news1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HotList extends Activity {

    private Toolbar toolbar;
    private TextView textView3,textView1,textView2;
    List<Integer> list = new ArrayList();
    private SQLiteDatabase db;
    private ListView heat_ListView;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotlist);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("爱乐——热榜");

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
        heat_ListView=(ListView)findViewById(R.id.heat_ListView);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.hotlist_items,tit);
        heat_ListView.setAdapter(adapter);
        //点击listview其中一项，显示新闻内容
        heat_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        initDate();
        Banner mbanner=(Banner) findViewById(R.id.banner);
        Banner banner = mbanner.setAdapter(new BannerImageAdapter<Integer>(list) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }

        });
        //是否允许自动轮播
        mbanner.isAutoLoop(true);
        //设置指示器
        mbanner.setIndicator(new CircleIndicator(this));
        //开始轮播
        mbanner.start();

        //完整榜单

        rl=findViewById(R.id.rl2);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_news_hotlist);
                Intent intent1 = new Intent(HotList.this, NewsHotList.class);
                startActivity(intent1);

            }
        });
    }
    private void initDate() {
        list.add(R.drawable.aaa);
        list.add(R.drawable.bbb);
        list.add(R.drawable.ccc);
        list.add(R.drawable.ddd);
    }

    public void fin1(View view){
        setContentView(R.layout.activity_hotlist);
    }
}