package com.example.music_news1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        EditText commentEditText = findViewById(R.id.editText_comment);
        Button sendButton = findViewById(R.id.button_send);

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

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.equals("")){
                    Intent loginIntent = new Intent(NewsDetailActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    Toast.makeText(NewsDetailActivity.this, "请先登录！！！！！！！！", Toast.LENGTH_SHORT).show();
                }else {
                    String comment = commentEditText.getText().toString();
                    if (comment.isEmpty()) {
                        Toast.makeText(NewsDetailActivity.this, "请输入评论", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues ctv = new ContentValues();
                        ctv.put("user_id", user);
                        ctv.put("news_id", id);
                        ctv.put("comment", comment);
                        ctv.put("time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                        db.insert("comment", null, ctv);

                        listView.setAdapter(new CommentAdapter(NewsDetailActivity.this, R.layout.item_view, addComments()));
                        Toast.makeText(NewsDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        ImageView dianzan=(ImageView)findViewById(R.id.imageView5) ;
        News news1=new News(tit);
        if (news1.isHasZan()) {
            dianzan.setImageDrawable(this.getResources().getDrawable(R.drawable.zan2));
        } else {
            dianzan.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_zan));
        }
        dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (news1.isHasZan()) {
                    news1.setHasZan(false);
                    news1.setHeat(news1.getHeat() - 1);
                    dianzan.setImageDrawable(getDrawable(R.drawable.ic_zan));
                } else {
                    news1.setHasZan(true);
                    news1.setHeat(news1.getHeat() + 1);
                    dianzan.setImageDrawable(getDrawable(R.drawable.zan2));
                }
            }
        });

        WebView webView = findViewById(R.id.details_content);
        webView.loadUrl("file:android_asset/news"+id+".html");

        listView.setAdapter(new CommentAdapter(this, R.layout.item_view, addComments()));


    }

    private Comment[] addComments() {
        Cursor cursor = db.rawQuery("select * from comment where news_id=?", new String[]{String.valueOf(id)});
        ArrayList<Comment> comments = new ArrayList<>();

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                comments.add(new Comment(cursor.getString(3), cursor.getString(4), cursor.getString(1), 100));
                cursor.moveToNext();
            }
        }

        return comments.toArray(new Comment[cursor.getCount()]);
    }
    public void fin1(View view){
        finish();
    }
}