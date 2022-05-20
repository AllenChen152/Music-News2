package com.example.music_news1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsDetailActivity extends Activity {

    private SQLiteDatabase  db;
    private int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        String database_path=getDatabasePath("UserStore.db").toString();
        db= SQLiteDatabase.openDatabase(database_path,null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        SharedPreferences sp=getSharedPreferences("user", Context.MODE_PRIVATE);
        String user=sp.getString("username","");

        TextView title=(TextView)findViewById(R.id.details_title) ;
        TextView author=(TextView)findViewById(R.id.author);
        TextView date=(TextView)findViewById(R.id.date);
        ListView listView = findViewById(R.id.listView);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String tit=bundle.getString("title");
        Cursor cursor = db.rawQuery("select * from news where title=?", new String[]{tit});

        String au=null;
        String d=null;
        if(cursor.moveToFirst()){
           id=cursor.getInt(0);
           au=cursor.getString(1);
           d=cursor.getString(3);
        }
        title.setText(tit);
        author.setText(au);
        date.setText(d);
        //int id=bundle.getInt("id");


        ImageView images=(ImageView)findViewById(R.id.imageView6) ;
        Cursor cursor1 = db.rawQuery("select * from favor where title=? and username=?", new String[]{tit,user});
        if(cursor1.getCount()>0 && !user.equals("")){
            images.setImageResource(R.drawable.ic_star_border_favourite_yes);
        }else{
            images.setImageResource(R.drawable.ic_star_border_favourite_no);

        }
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor2 = db.rawQuery("select * from favor where title=? and username=?", new String[]{tit,user});
                if(user.equals("")){
                    Intent loginIntent = new Intent(NewsDetailActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    Toast.makeText(NewsDetailActivity.this, "请先登录！！！！！！！！", Toast.LENGTH_SHORT).show();
                }else {
                    if (cursor2.getCount() > 0) {
                        images.setImageResource(R.drawable.ic_star_border_favourite_no);
                        db.delete("favor", "title=?", new String[]{tit});
                        Toast.makeText(NewsDetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    } else {
                        images.setImageResource(R.drawable.ic_star_border_favourite_yes);
                        ContentValues ctv = new ContentValues();
                        ctv.put("username", user);
                        ctv.put("news_id", id);
                        ctv.put("title", tit);
                        db.insert("favor", null, ctv);
                        Toast.makeText(NewsDetailActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        WebView webView = findViewById(R.id.details_content);
        webView.loadUrl("file:android_asset/news"+id+".html");

        listView.setAdapter(new CommentAdapter(this, R.layout.item_view, addComments()));


    }

    private Comment[] addComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("用户1", "2022.04.30 07.24", "内容内容", 23));
        comments.add(new Comment("用户2", "2022.04.30 07.24", "内容内容", 23));
        comments.add(new Comment("用户3", "2022.04.30 07.24", "内容内容", 23));
        comments.add(new Comment("用户4", "2022.04.30 07.24", "内容内容", 23));
        comments.add(new Comment("用户5", "2022.04.30 07.24", "内容内容", 23));
        comments.add(new Comment("用户6", "2022.04.30 07.24", "内容内容", 23));
        return comments.toArray(new Comment[6]);
    }
    public void fin1(View view){
        finish();
    }
}