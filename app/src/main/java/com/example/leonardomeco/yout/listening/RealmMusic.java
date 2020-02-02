package com.example.leonardomeco.yout.listening;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmMusic extends RealmObject {
    @PrimaryKey
    @Required
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
