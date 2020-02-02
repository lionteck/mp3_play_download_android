package com.example.leonardomeco.yout.listening;

import android.content.Context;
import android.database.DataSetObserver;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leonardomeco.yout.R;
import com.example.leonardomeco.yout.main.MainActivityYout;

import java.util.ArrayList;

public class MusicListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ArrayList<Music> musics;
    View selectedView;
    Context context;
    MusicInterface inter;
    int selected;
    MusicListAdapter(ArrayList<Music> musics, Context context,MusicInterface inter){
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        this.inter=inter;
        selected=-1;
        Music temp;
        this.context=context;
        this.musics=musics;


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

    public void addItem(Music add) {
        musics.add(add);
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Object getItem(int position) {
        return musics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return musics.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_musics, parent, false);
        TextView title=rowView.findViewById(R.id.title);
        TextView author=rowView.findViewById(R.id.author);
        title.setText(musics.get(position).getTitle());
        author.setText(musics.get(position).getAuthor());
        rowView.setTag(musics.get(position).getPath().getAbsolutePath());
        if(position==selected){
            StyleSelect(rowView);
        }
        return rowView;
    }


    public void StyleSelect(View view){
        /*((TextView) view.findViewById(R.id.title)).setTextColor(Color.WHITE);
        ((TextView) view.findViewById(R.id.author)).setTextColor(Color.WHITE);
        view.setBackgroundColor(Color.BLACK);*/
        view.setBackground(context.getResources().getDrawable(R.drawable.bg_list_hover));
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return musics.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Log.i("info",musics.get(position).getPath().getAbsolutePath());

        inter.setMusicSong(musics.get(position),MainActivityYout.CHANGE_SONGS);
        selected=position;
        selectedView=view;


    }

    public void nextSong(){

        selected++;

        if(selected<getCount()){

            inter.setMusicSong(musics.get(selected),MainActivityYout.CHANGE_SONGS);
        }
        else{
            selected=-1;
        }
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        inter.setMusicSong(musics.get(position),MainActivityYout.SAVE_PLAYLIST);
        return true;
    }
}
