package com.example.leonardomeco.yout.youtube;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leonardomeco.yout.R;
import com.example.leonardomeco.yout.downloads.AsynchDownloadInteface;
import com.example.leonardomeco.yout.downloads.AsynchTaskDownload;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class YoutubeListAdapter extends BaseAdapter {
    ArrayList<YoutubeVideo> videos;
    Context context;
    YoutubeListAdapter( ArrayList<YoutubeVideo> videos, Context context){
        this.videos=videos;
        this.context=context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_youtube, parent, false);
        ImageView image=rowView.findViewById(R.id.image_video);
        TextView author=rowView.findViewById(R.id.author_video);
        TextView title=rowView.findViewById(R.id.title_video);
        rowView.setTag(videos.get(position).getVideoId());
        author.setText(videos.get(position).getAuthor());
        title.setText(videos.get(position).getTitle());
        Picasso.get()
                .load(videos.get(position).getCover())
                .into(image);


        return rowView;
    }




    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return videos.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }




}
