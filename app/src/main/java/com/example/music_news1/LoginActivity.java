package com.example.music_news1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private SQLiteDatabase  db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //连接到数据库
        String database_path=getDatabasePath("UserStore.db").toString();
        db= SQLiteDatabase.openDatabase(database_path,null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
    }

    public void regis(View view){
        Intent intent = new Intent(	LoginActivity.this,Register.class);
        startActivity(intent);
    }

    public void login(View view){
        String user=((EditText)findViewById(R.id.rg_username)).getText().toString();
        String pwd=((EditText)findViewById(R.id.rg_password)).getText().toString();
        if (login(user,pwd)) {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            SharedPreferences.Editor ed=getSharedPreferences("user",MODE_PRIVATE).edit();
            ed.putString("username",user);
            ed.commit();
            startActivity(intent);
            Toast toast=Toast.makeText(LoginActivity.this,"登陆成功！！！！",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
        else {
            Toast toast=Toast.makeText(LoginActivity.this,"输入的账号密码错误！！！！",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }
    public boolean login(String username,String password) {
        String sql = "select * from user where username=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
    public void fin1(View view){
        finish();
    }

}