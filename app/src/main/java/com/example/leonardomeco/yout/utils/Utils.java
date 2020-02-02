package com.example.leonardomeco.yout.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.leonardomeco.yout.listening.Music;
import com.example.leonardomeco.yout.listening.RealmMusic;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.realm.RealmResults;

public class Utils {
    public static File[] RealmObjectToFiles(RealmResults<RealmMusic> musics){
        File musics_files[]=new File[musics.size()];
        for(int x=0;x<musics.size();x++){
            musics_files[x]=new File(musics.get(x).getPath());
        }
        return musics_files;
    }

    public static Music SingleFileToMusics(File music) {
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        Music temp=new Music();
        metaRetriver.setDataSource(music.getAbsolutePath());
        byte image[] = metaRetriver.getEmbeddedPicture();
        if (image != null) {
            BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        temp.setAuthor(metaRetriver
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        temp.setTitle(metaRetriver
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        temp.setPath(music.getAbsolutePath());
        return temp;
    }

    public static ArrayList<Music> FileToMusics(File musics[]){
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        Music temp;
        ArrayList<Music> ret=new ArrayList<>();
        for (int x = 0; x < musics.length; x++) {
            if(musics[x].getName().endsWith("mp3")) {
                temp = new Music();
                metaRetriver.setDataSource(musics[x].getAbsolutePath());
                byte image[] = metaRetriver.getEmbeddedPicture();
                if (image != null) {
                    BitmapFactory.decodeByteArray(image, 0, image.length);
                }
                temp.setAuthor(metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                temp.setTitle(metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
                temp.setPath(musics[x].getAbsolutePath());
                ret.add(temp);
            }

        }
        return ret;

    }

    public static JSONObject sendVolleyRequest(String url, Context cont){
        RequestQueue queue = Volley.newRequestQueue(cont);
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.GET, url, future, future);
        Log.i("url",url);
        future.setRequest(request);
        queue.add(request);
        try {

            String res_string = future.get(60, TimeUnit.SECONDS); // this will block (forever)
            Log.i("request", "do2");
            //res_string = res_string.substring(1).substring(0, res_string.length() - 2);
            try {
                Log.i("string",res_string);
                JSONObject json_ret = new JSONObject(res_string);
                return json_ret;


            } catch (JSONException e) {
                Log.e("string",res_string);
                e.printStackTrace();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
