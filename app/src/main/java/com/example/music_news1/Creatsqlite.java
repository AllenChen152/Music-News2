package com.example.music_news1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Creatsqlite extends SQLiteOpenHelper {
    private Context mContext;
    public Creatsqlite(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
        mContext=context;
    }
    //只在创建的时候用一次
    public void onCreate(SQLiteDatabase db) {
        String sql_u="create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),name varchar(20))";
        String sql_s="create table fav(id integer primary key autoincrement,username varchar(20),password varchar(20),name varchar(20))";
        db.execSQL(sql_u);
        db.execSQL(sql_s);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
