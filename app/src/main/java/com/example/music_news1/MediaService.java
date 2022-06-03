package com.example.music_news1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MediaService extends Service {

    private final MyBinder binder = new MyBinder();

    private static MediaPlayer mediaPlayer = new MediaPlayer();

    public MediaService() {
    }

    public class MyBinder extends Binder {
        public Service getService() {
            return MediaService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        //media初始化工作
        return binder;
    }

    public boolean getMediaPlayerStatus() {
        return mediaPlayer.isPlaying();
    }

    public void toPause() {
        mediaPlayer.pause();
    }

    public void toStart() {
        mediaPlayer.start();
    }

    /**
     * 播放组件
     */
    public void toPlay(MediaPlayerCallback callback){

        try {
            mediaPlayer.reset();

            mediaPlayer.setDataSource(getResources().openRawResourceFd(R.raw.music1));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    callback.onPrepared(mediaPlayer.getDuration());

                }
            });

        }catch (Exception e){
            System.out.println(e);
        }

        //开启新线程获取实时播放位置
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    while (true){
                        sleep(1000);    //为了防止内存占用过多，需要休眠
                        if (mediaPlayer.isPlaying()){

                            callback.onUpdate(mediaPlayer.getCurrentPosition());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}