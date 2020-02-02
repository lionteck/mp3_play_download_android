package com.example.leonardomeco.yout.playlists;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.leonardomeco.yout.R;
import com.example.leonardomeco.yout.listening.MusicList;

public class ChooseFile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("choose files...");
        FragmentManager transaction = getSupportFragmentManager();
        transaction.beginTransaction().add(R.id.list_files, MusicList.newInstance("","")).commit();
    }

}
