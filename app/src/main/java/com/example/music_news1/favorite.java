package com.example.music_news1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class favorite extends Activity {

    private SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        String database_path = getDatabasePath("UserStore.db").toString();
        db = SQLiteDatabase.openDatabase(database_path, null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);

        SharedPreferences sp=getSharedPreferences("user",MODE_PRIVATE);
        String user=sp.getString("username","");
        Cursor cursor = db.rawQuery("select * from favor where username=?", new String[]{user});
        String[] title=new String[cursor.getCount()];
        if(cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++){
                title[i]=cursor.getString(2);
                cursor.moveToNext();

            }
        }
        ListView list=(ListView) findViewById(R.id.favorite_newsList);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,title);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(favorite.this, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title[position]);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        });
    }
}
