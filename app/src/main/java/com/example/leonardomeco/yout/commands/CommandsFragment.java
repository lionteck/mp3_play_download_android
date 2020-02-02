package com.example.leonardomeco.yout.commands;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.leonardomeco.yout.listening.Music;
import com.example.leonardomeco.yout.R;

import java.io.IOException;


public class CommandsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Handler mHandler = new Handler();
    float pass;
    final int SECONDS_IN_A_MINUTE  = 60;
    int sec,new_sec,min,length;
    boolean blockBar;
    MediaPlayer mediaPlayer;
    int prog,new_prog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private ImageView play,pause;
    private String mParam2;
    private boolean stop=true;
    private View commandsView;
    private OnCommandInteraction mListener;
    SeekBar seek;
    TextView positionView,endView,title;
    public CommandsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommandsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommandsFragment newInstance(String param1, String param2) {
        CommandsFragment fragment = new CommandsFragment();
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
        if(commandsView==null)
            commandsView=inflater.inflate(R.layout.audio_commands_layout,container, false);
        seek=commandsView.findViewById(R.id.seek_bar);
        play=commandsView.findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(length);
                Log.i("lenght",length+"");
                mediaPlayer.start();
                blockBar=false;
            }
        });
        pause=commandsView.findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blockBar=true;
                length=mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();

            }
        });
        positionView=commandsView.findViewById(R.id.position);
        endView=commandsView.findViewById(R.id.end);
        title=commandsView.findViewById(R.id.title);
        seek.setOnSeekBarChangeListener(this);
        mHandler=new Handler();
        prog=0;
        sec=0;
        min=0;
        positionView.setText(TimeFormat(sec));
        new_sec=0;
        blockBar=true;
        new_prog=0;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        Runnable runnable = new Runnable() {
            public void run() {
                if (stop) {
                    if (mediaPlayer.isPlaying() && !blockBar) {

                        new_prog = (int) Math.floor(mediaPlayer.getCurrentPosition() * pass / 1000);
                        new_sec = (mediaPlayer.getCurrentPosition() / 1000);
                        if (prog != new_prog) {
                            prog = new_prog;
                            seek.setProgress(new_prog);
                            seek.invalidate();
                        }
                        if (new_sec != sec) {
                            sec = new_sec;
                            positionView.setText(TimeFormat(sec));
                        }
                    } else if (!mediaPlayer.isPlaying() && !blockBar) {
                        blockBar = true;
                        mListener.nextSong();
                    }
                    mHandler.postDelayed(this, 300);
                }
            }
        };

         mHandler.post(runnable);
        return commandsView;
    }

    public void setStop(){
        stop=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop=false;
        Log.i("destroy","true");
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCommandInteraction) {
            mListener = (OnCommandInteraction) context;
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
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        blockBar=true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        new_prog=seekBar.getProgress();
        prog=new_prog;
        mediaPlayer.seekTo((int)((new_prog*1000)/(pass)));
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
        blockBar=false;

    }



    public String TimeFormat(int sec){
        return ZeroFill(sec/60)+":"+ZeroFill(sec%60);
    }


    public String ZeroFill(int num){
        return num<10?"0"+num:num+"";
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


    public void playSong(Music song){
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(getContext(),Uri.fromFile(song.getPath()));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int duration=mediaPlayer.getDuration()/1000;
        title.setText(song.getAuthor()+" "+song.getTitle());
        endView.setText(TimeFormat(duration));
        mediaPlayer.start();
        pass=100000f/(float)mediaPlayer.getDuration();
        blockBar=false;
    }
}
