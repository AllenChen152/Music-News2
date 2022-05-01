package com.example.music_news1;

public class Comment {
    private String userId;

    private String time;

    private String message;

    private int zan;

    public Comment(String userId, String time, String message, int zan) {
        this.userId = userId;
        this.time = time;
        this.message = message;
        this.zan = zan;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}
