package com.example.music_news1.Fragement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.music_news1.Follow;
import com.example.music_news1.LoginActivity;
import com.example.music_news1.R;
import com.example.music_news1.Setting;
import com.example.music_news1.tools.ImageRound;

public class PerenFragement extends Fragment {
    private Toolbar toolbar;
    private SQLiteDatabase  db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_person, container, false);
        String database_path=getContext().getDatabasePath("UserStore.db").toString();
        db= SQLiteDatabase.openDatabase(database_path,null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("爱乐——个人中心");

        SharedPreferences sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String user=sp.getString("username","");
        String sql = "select * from user where username=?";
        Cursor cursor = db.rawQuery(sql, new String[] {user});
        while(cursor.moveToNext()) {
            String name=cursor.getString(3);
            TextView text=(TextView) view.findViewById(R.id.textView);
            text.setText(name);
        }
        return view;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        ImageRound Alogin=(ImageRound) getView().findViewById(R.id.ImageView2);
        Alogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                String user=sp.getString("username","");
                if(user.equals("")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "用户名已登录！！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RelativeLayout shezhi=(RelativeLayout) getView().findViewById(R.id.shezhi);
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(	getActivity(), Setting.class);
                startActivity(intent);
            }
        });

        RelativeLayout guan=(RelativeLayout) getView().findViewById(R.id.guanzhu);
        guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                String user=sp.getString("username","");
                if(user.equals("")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), Follow.class);
                    startActivity(intent);
                }
            }
        });


    }

}
