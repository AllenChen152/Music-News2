package com.example.music_news1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private SQLiteDatabase db;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String database_path= getDatabasePath("UserStore.db").toString();
        db = SQLiteDatabase.openDatabase(database_path,null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);

        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initSearch(newText);
                return true;
            }
        });
    }

    private void initSearch(String query) {
        Cursor cursor=db.rawQuery("select * from news",null);
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        String[] tit=new String[cursor.getCount()];
        String[] author=new String[cursor.getCount()];
        String[] date=new String[cursor.getCount()];
        int[] id=new int[cursor.getCount()];
        if(cursor.moveToFirst()){
            for(int j=0;j<cursor.getCount();j++){
                HashMap<String, Object> map = new HashMap<String, Object>();
                String t=cursor.getString(2);
                String a =cursor.getString(1);
                String d=cursor.getString(3);
                int i=cursor.getInt(0);
                if (query.isEmpty()) break;
                //System.out.println(tit[j]);
                if (t.contains(query) || a.contains(query) || d.contains(query)) {
                    author[list.size()] = a;
                    date[list.size()] = d;
                    id[list.size()] = i;
                    tit[list.size()] = t;
                    map.put("title",tit[j]);
                    list.add(map);
                }
                cursor.moveToNext();
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter mAdapter=new RecyclerViewAdapter(list,tit);
        recyclerView.setAdapter(mAdapter);

        //新闻具体内容
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent=new Intent(view.getContext(), NewsDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",id[position]);
                bundle.putString("title",tit[position]);
                bundle.putString("author",author[position]);
                bundle.putString("date",date[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}