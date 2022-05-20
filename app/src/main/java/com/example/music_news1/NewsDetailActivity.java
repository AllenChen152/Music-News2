package com.example.music_news1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);

        TextView title=(TextView)findViewById(R.id.details_title) ;
        TextView author=(TextView)findViewById(R.id.author);
        TextView date=(TextView)findViewById(R.id.date);
        ListView listView = findViewById(R.id.listView);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        title.setText(bundle.getString("title"));
        author.setText(bundle.getString("author"));
        date.setText(bundle.getString("date"));
        int id=bundle.getInt("id");


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