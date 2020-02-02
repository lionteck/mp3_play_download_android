package com.example.leonardomeco.yout.downloads;

import android.os.AsyncTask;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.example.leonardomeco.yout.listening.Music;
import com.example.leonardomeco.yout.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AsynchTaskDownload extends AsyncTask<String, Integer,Boolean> {
    AsynchDownloadInteface inter;
    String filename;
    public AsynchTaskDownload(AsynchDownloadInteface inter){
        this.inter=inter;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String artist_str=strings[0];
        String video_str=strings[1];
        String title_str=strings[2];
        if(artist_str.compareTo("")!=0 && video_str.compareTo("")!=0 && title_str.compareTo("")!=0){
            OkHttpClient client = new OkHttpClient();

            String base=String.valueOf(Base64.encodeToString(("youtube.com/watch?v="+video_str).getBytes(), Base64.DEFAULT));
           Log.i("base",base);
            RequestBody body=new FormBody.Builder().add("video_id", video_str).add("video_url",base).add("format","mp3").add("title",title_str).add("artist",artist_str).add("start_time","false").add("end_time","false").add("thingy","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbF9mb3JfYXBpX2FjY2VzcyI6ImpvaG5AbmFkZXIubXgifQ.YPt3Eb3xKekv2L3KObNqMF25vc2uVCC-aDPIN2vktmA").add("audio_quality","128k").build();
            Request request = new Request.Builder()
                    .url("https://dvr.yout.com/mp3").post(body)
                    .build();
            try {
                filename=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+artist_str+"-"+title_str+".mp3";
                Response response = client.newCall(request).execute();
                InputStream stream=response.body().byteStream();
                FileOutputStream out=new FileOutputStream(filename);
                byte bytes[]=new byte[128];
                int len=0;
                while((len=stream.read(bytes))!=-1){
                    out.write(bytes, 0, len);
                }
                out.close();
            } catch (IOException ex) {

                Log.e("exception",ex.getMessage());
                return false;

            }
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }


    protected void onPostExecute(Boolean  result) {
        if(result) {
            Log.i("success", "OK");
            Music mus=Utils.SingleFileToMusics(new File(filename));
            inter.finishDownload(mus);
        }
        else{
            Log.i("success", "NO");
        }

    }
}
