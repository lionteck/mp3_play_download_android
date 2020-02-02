package com.example.leonardomeco.yout.playlists;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leonardomeco.yout.listening.MusicInterface;
import com.example.leonardomeco.yout.R;

import io.realm.RealmResults;

public class PlaylistListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    RealmResults<RealmPlaylist> names;
    Context context;
    MusicInterface inter;
    int selected;
    PlaylistListAdapter(RealmResults<RealmPlaylist> names, Context context){
        this.inter=inter;
        selected=-1;
        this.context=context;
        this.names=names;


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
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
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
        View rowView = inflater.inflate(R.layout.list_playlist, parent, false);
        TextView nome=rowView.findViewById(R.id.nome_play);
        nome.setText(names.get(position).getNome());
        return rowView;
    }




    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return names.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


}
