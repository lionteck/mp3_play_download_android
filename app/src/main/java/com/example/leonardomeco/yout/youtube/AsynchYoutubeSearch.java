package com.example.leonardomeco.yout.youtube;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.leonardomeco.yout.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class AsynchYoutubeSearch extends AsyncTask<String, Integer,ArrayList<YoutubeVideo>> {
    Context cont;
    YoutubeSearchInterface searchInterface;

    public AsynchYoutubeSearch(Context cont,YoutubeSearchInterface inter){

       this.searchInterface=inter;
        this.cont=cont;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ArrayList<YoutubeVideo>  doInBackground(String... strings) {
        String url="https://www.googleapis.com/youtube/v3/search?q="+strings[0]+"&part=snippet&key=AIzaSyBGxW87TzfQSu4oQjk844oEcq0d2V4TsVk";
        JSONObject obj=Utils.sendVolleyRequest(url, cont);
        ArrayList<YoutubeVideo> videos = null;
        if(obj!=null) {
           YoutubeVideo video;
            try {
                videos = new ArrayList<>();
                JSONArray videos_json = obj.getJSONArray("items");
                for (int x = 0; x < videos_json.length(); x++) {
                    video = new YoutubeVideo();
                    video.setVideoId(videos_json.getJSONObject(x).getJSONObject("id").getString("videoId"));
                    video.setAuthor(videos_json.getJSONObject(x).getJSONObject("snippet").getString("title"));
                    video.setTitle(videos_json.getJSONObject(x).getJSONObject("snippet").getString("description"));
                    video.setCover(videos_json.getJSONObject(x).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url"));
                    videos.add(video);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return videos;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }


    protected void onPostExecute(ArrayList<YoutubeVideo> result) {
        if(result!=null)
            searchInterface.loadSearchList(result);

    }

    public interface YoutubeSearchInterface{
        void loadSearchList(ArrayList<YoutubeVideo> result);
    }
}
