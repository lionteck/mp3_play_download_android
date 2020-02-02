package com.example.leonardomeco.yout.listening;

import java.io.File;

public class Music {
    private String title,author;
    long id;
    String path;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public File getPath() {
        return new File(path);
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
