package com.example.music_news1.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra("level", 0);
        //电量的总刻度
        myListener.onListener(level+"");
        System.out.println(level + "%");

    }
    public MyListener myListener;
    public interface MyListener {
        public void onListener(String level);
    }
    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }
}
