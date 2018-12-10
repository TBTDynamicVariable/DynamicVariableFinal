package com.wordpress.mentalhealthmonitor.dynamicvariable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;

public class MySettings extends AppCompatActivity {

    public Button VolumeOnButton;
    public Button VolumeOffButton;
    MediaPlayer music;
    private Button toMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);

        VolumeOnButton = (Button) findViewById(R.id.VolumeOn);
        VolumeOffButton = (Button) findViewById(R.id.VolumeOff);
        music=MediaPlayer.create(MySettings.this,R.raw.song);
        toMain = (Button) findViewById(R.id.mainButSettings);



        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });


    }
    public void openMain(){
       finish();
    }


    public void clkplay(View v){
        music.start();
        music.setLooping(true);//This turns the volume on

    }
    public void clkpause(View v){
        music.pause();//Thus turns the volume off
    }
}
