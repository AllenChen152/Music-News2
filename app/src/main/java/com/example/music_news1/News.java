package com.example.music_news1;

public class News {
    private int id;
    private String author;
    private String title;
    private String publish_date;
    private int heat;
    private boolean hasZan = false;

    public News(String title) {
        //this.userId = userId;
        this.title = title;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public boolean isHasZan() {
        return hasZan;
    }

    public void setHasZan(boolean hasZan) {
        this.hasZan = hasZan;
    }


}
