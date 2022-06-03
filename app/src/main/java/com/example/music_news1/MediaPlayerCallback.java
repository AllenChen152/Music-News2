package com.example.music_news1;

public interface MediaPlayerCallback {
    void onPrepared(int max);
    void onUpdate(int current);
}
