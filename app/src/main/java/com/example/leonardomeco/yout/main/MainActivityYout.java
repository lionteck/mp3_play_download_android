package com.example.leonardomeco.yout.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.leonardomeco.yout.downloads.AsynchDownloadInteface;
import com.example.leonardomeco.yout.youtube.AsynchYoutubeSearch;
import com.example.leonardomeco.yout.commands.CommandsFragment;
import com.example.leonardomeco.yout.downloads.DownloadFragment;
import com.example.leonardomeco.yout.menu.MenuFragment;
import com.example.leonardomeco.yout.menu.MenuSearchInterface;
import com.example.leonardomeco.yout.listening.Music;
import com.example.leonardomeco.yout.listening.MusicList;
import com.example.leonardomeco.yout.commands.OnCommandInteraction;
import com.example.leonardomeco.yout.playlists.PlaylistList;
import com.example.leonardomeco.yout.R;
import com.example.leonardomeco.yout.listening.RealmMusic;
import com.example.leonardomeco.yout.youtube.YoutubeSearch;
import com.example.leonardomeco.yout.youtube.YoutubeVideo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class MainActivityYout extends AppCompatActivity implements AsynchYoutubeSearch.YoutubeSearchInterface,MenuSearchInterface,MenuFragment.OnFragmentMenuListener,FragmentInteraction, OnCommandInteraction, AsynchDownloadInteface {
    final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE=1;
    public static int CHANGE_SONGS=0;
    public static int SAVE_PLAYLIST=1;
    MenuItem item;
    CommandsFragment comm;
    MenuFragment menu;
    String open;
    Realm realm;
    SearchView searchView;
    MusicList music_run;
    YoutubeSearch music_run_play;
    PlaylistList playlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_yout);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            Log.w("permission","denied");
        }
        open="1";
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });*/

        //((AudioManager)getSystemService(AUDIO_SERVICE)).registerMediaButtonEventReceiver(new ComponentName(this, MediaButtonReceiver.class));
        comm=CommandsFragment.newInstance("","");
        FragmentManager transaction = getSupportFragmentManager();
        transaction.beginTransaction().add(R.id.commands, comm).commit();
        //String path=Environment.getExternalStorageDirectory().getPath() + "/" +name;

        //YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        music_run=MusicList.newInstance("","");
        //music_run_play=MusicListRuntime.newInstance("","");
        playlist=PlaylistList.newInstance("","");
       // music_run_play=DownloadFragment.newInstance("","");
        music_run_play=YoutubeSearch.newInstance("","");
        transaction.beginTransaction().add(R.id.youtube,music_run).commit();
        menu=MenuFragment.newInstance("","");
        transaction.beginTransaction().add(R.id.menu,menu).commit();

        /*youTubePlayerFragment.initialize("AIzaSyBalVpIByQMGZkYt1chZNY2K64l4hN8-Z4", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                 youTubePlayer.loadVideo("O72BKIGLpnQ");
                Log.i("auth","success");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.i("auth","no");
            }
        });*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("permission","granted");
                } else {
                    Log.i("permission","denied2");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onFragmentInteraction(Music music, int command) {
        Log.i("info",music.getPath().getAbsolutePath());
        if(command==SAVE_PLAYLIST) {
            final Music fin = music;
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        realm.createObject(RealmMusic.class, fin.getPath().getAbsolutePath());
                    } catch (RealmPrimaryKeyConstraintException e) {
                        Log.i("realm", "exists");
                    }
                }
            });
        }
        else if(command==CHANGE_SONGS) {
            comm.playSong(music);
        }
    }

    @Override
    public void nextSong() {
        if(open.compareTo("1")==0) {
            music_run.nextSong();
        }
        else{
           // music_run_play.nextSong();
        }

    }




    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.activity_menu, menu);
         item=menu.findItem(R.id.action_search);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
               Log.i("action",menuItem.isActionViewExpanded()+"");
                return true;
            }
        });
         item.setVisible(false);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("query",query);
                searchView.clearFocus();
                music_run_play.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("query",newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public void onClickItem(String commands) {
        FragmentManager transaction = getSupportFragmentManager();

        if(commands.compareTo("2")==0){
            open="2";
            transaction.beginTransaction().replace(R.id.youtube,music_run_play).commit();

        }
        else if(commands.compareTo("1")==0){
            open="1";
            transaction.beginTransaction().replace(R.id.youtube,music_run).commit();
            Log.i("i",commands);
        }
        else if(commands.compareTo("3")==0){
            open="3";
            transaction.beginTransaction().replace(R.id.youtube,playlist).commit();
            Log.i("i",commands);
        }

    }

    @Override
    public void finishDownload(Music mus) {
        music_run.addSong(mus);
    }

    @Override
    public void showSearch(boolean show) {
        if(item!=null)
            item.setVisible(show);
    }

    @Override
    public void loadSearchList(ArrayList<YoutubeVideo> result) {
        for(YoutubeVideo video : result){
            Log.i("video",video.getVideoId());
        }
    }
}
