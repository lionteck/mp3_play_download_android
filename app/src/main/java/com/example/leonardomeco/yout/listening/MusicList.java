package com.example.leonardomeco.yout.listening;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.leonardomeco.yout.main.FragmentInteraction;
import com.example.leonardomeco.yout.menu.MenuSearchInterface;
import com.example.leonardomeco.yout.R;
import com.example.leonardomeco.yout.utils.Utils;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicList extends Fragment implements MusicInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View main;
    MusicListAdapter adapter;
    MenuSearchInterface searchint;
    ListView list;
    private FragmentInteraction mListener;

    public MusicList() {
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
    public static MusicList newInstance(String param1, String param2) {
        MusicList fragment = new MusicList();
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

        if(main==null) {
            main = inflater.inflate(R.layout.fragment_music_list, container, false);
            list = main.findViewById(R.id.music_list);
            Realm realm = Realm.getDefaultInstance();
            File dir_music = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            ArrayList<Music> musics = Utils.FileToMusics(dir_music.listFiles());
            //musics.add(dir_music.listFiles());
            if(musics.size()>0) {
                adapter = new MusicListAdapter(musics, this.getContext(), this);
                list.setAdapter(adapter);
                list.setOnItemClickListener(adapter);
                list.setOnItemLongClickListener(adapter);
                list.setLongClickable(true);
            }
        }
        adapter.notifyDataSetChanged();

        searchint.showSearch(false);
        getActivity().setTitle("home");
        Log.i("selected",adapter.selected+"");
        return main;
    }

    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteraction) {
            mListener = (FragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof MenuSearchInterface) {
            searchint = (MenuSearchInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    public void addSong(Music mus){
        adapter.addItem(mus);
        adapter.notifyDataSetChanged();
        list.invalidateViews();
    }

    public void nextSong() {

        adapter.nextSong();

        list.invalidateViews();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void setMusicSong(Music music,int command) {
        mListener.onFragmentInteraction(music,command);
        list.invalidateViews();

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Music uri,int command);
    }
}
