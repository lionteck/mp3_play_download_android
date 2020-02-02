package com.example.leonardomeco.yout;




import com.example.leonardomeco.yout.listening.RealmMusic;

import io.realm.RealmList;
import io.realm.RealmObject;


public class RuntimeMusics extends RealmObject {

    private RealmList<RealmMusic> musics;

    public RealmList<RealmMusic> getMusics() {
        return musics;
    }

    public void setMusics(RealmList<RealmMusic> musics) {
        this.musics = musics;
    }
}
