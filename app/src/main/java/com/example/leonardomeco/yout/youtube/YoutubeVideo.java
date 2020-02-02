package com.example.leonardomeco.yout.youtube;

import android.graphics.Bitmap;


public class YoutubeVideo {
    private String videoId;
    private String cover;
    private String title;
    private String author;

    public String getCover() {
        return cover;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
