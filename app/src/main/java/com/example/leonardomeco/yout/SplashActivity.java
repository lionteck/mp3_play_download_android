package com.example.leonardomeco.yout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.leonardomeco.yout.main.MainActivityYout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);
        final Intent intent=new Intent(this,MainActivityYout.class);
        Runnable loadAct=new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        Handler handler=new Handler();
        handler.postDelayed(loadAct,2000);

    }

}
