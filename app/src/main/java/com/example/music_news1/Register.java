package com.example.music_news1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private Creatsqlite dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new Creatsqlite(this, "UserStore.db", null, 1);
    }

    public void register(View view){

        String user=((EditText)findViewById(R.id.rg_username)).getText().toString();
        String pwd=((EditText)findViewById(R.id.rg_password)).getText().toString();
        String name=((EditText)findViewById(R.id.rg_name)).getText().toString();
        int result=saveUser(user,pwd,name);
        if(result==1){
            Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(	Register.this,LoginActivity.class);
            startActivity(intent);
        }else if(result==-1){
            Toast.makeText(Register.this, "用户名已经存在！！", Toast.LENGTH_SHORT).show();
        }else if(result==0){
            Toast.makeText(Register.this, "用户名密码为空！", Toast.LENGTH_SHORT).show();
        }


    }

    public int saveUser(String user,String pwd,String name){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        int i;
        if(user!=null){
            Cursor cursor = db.rawQuery("select * from user where username=?", new String[]{user});
            if(cursor.getCount()>0){
                i=-1;
            }else{
                ContentValues ctv=new ContentValues ();
                ctv.put("username",user);
                ctv.put("password",pwd);
                ctv.put("name",name);
                db.insert("user",null,ctv);
                i=1;
            }
        }else{
            i=0;
        }
        return i;
    }

    public void fin2(View view){
        finish();
    }
}