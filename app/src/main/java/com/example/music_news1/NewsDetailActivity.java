package com.example.music_news1;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * 类名记得要大写噢
 */
public class NewsDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);

        WebView webView = findViewById(R.id.details_content);

        ListView listView = findViewById(R.id.listView);

        listView.setAdapter(new CommentAdapter(this, R.layout.item_view, addComments()));

        webView.loadUrl("file:android_asset/news.html");
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
}