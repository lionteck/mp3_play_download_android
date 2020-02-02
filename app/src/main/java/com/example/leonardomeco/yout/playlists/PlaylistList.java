package com.example.leonardomeco.yout.playlists;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.leonardomeco.yout.main.FragmentInteraction;
import com.example.leonardomeco.yout.listening.Music;
import com.example.leonardomeco.yout.listening.MusicInterface;
import com.example.leonardomeco.yout.R;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;


public class PlaylistList extends Fragment implements MusicInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    PlaylistListAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Realm realm;
    View main;

    private FragmentInteraction mListener;

    public PlaylistList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicList.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaylistList newInstance(String param1, String param2) {
        PlaylistList fragment = new PlaylistList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(main==null)
            main=inflater.inflate(R.layout.fragment_music_playlist, container, false);
        final ListView list=main.findViewById(R.id.music_list);
        realm = Realm.getDefaultInstance();
        RealmResults<RealmPlaylist> results = realm.where(RealmPlaylist.class).findAll();
        if(results.size()>0) {
            Log.i("runtime", "enter");
            //musics.add(musics_run);
            adapter = new PlaylistListAdapter(results, this.getContext());
            list.setAdapter(adapter);
            list.setOnItemClickListener(adapter);
        }
        getActivity().setTitle("playlist");
        FloatingActionButton playlists= main.findViewById(R.id.new_play);
        playlists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.playlist_dialog, null);
                final EditText playNome = dialogView.findViewById(R.id.nome);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setView(dialogView);
                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(!playNome.getText().toString().contentEquals("")) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    try {
                                        realm.createObject(RealmPlaylist.class,playNome.getText().toString());
                                        RealmResults<RealmPlaylist> results = realm.where(RealmPlaylist.class).findAll();
                                        adapter = new PlaylistListAdapter(results, getContext());
                                        list.setAdapter(adapter);
                                        list.setOnItemClickListener(adapter);
                                    } catch (RealmPrimaryKeyConstraintException e) {
                                        Log.i("realm", "exists");
                                    }
                                }
                            });
                        }
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();

            }
        });


        return main;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteraction) {
            mListener = (FragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setMusicSong(Music music, int command) {
        mListener.onFragmentInteraction(music,command);


    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
