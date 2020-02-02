package com.example.leonardomeco.yout.playlists;

import com.example.leonardomeco.yout.listening.RealmMusic;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmPlaylist  extends RealmObject {
    @PrimaryKey
    @Required
    private String nome;
    private RealmList<RealmMusic> songs;

    public RealmList<RealmMusic> getSongs() {
        return songs;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSongs(RealmList<RealmMusic> songs) {
        this.songs = songs;
    }
}
