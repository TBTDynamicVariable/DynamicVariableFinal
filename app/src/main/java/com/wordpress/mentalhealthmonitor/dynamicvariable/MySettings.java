package com.wordpress.mentalhealthmonitor.dynamicvariable;

import android.content.Context;
import android.media.AudioManager;
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

        VolumeOnButton = findViewById(R.id.VolumeOn);
        VolumeOffButton = findViewById(R.id.VolumeOff);
        music=MediaPlayer.create(MySettings.this,R.raw.song);
        final AudioManager ourMusic = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        toMain = findViewById(R.id.mainButSettings);



        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });
        VolumeOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clkplay(ourMusic);
            }
        });
        VolumeOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clkpause(ourMusic);
            }
        });


    }
    public void openMain(){ finish();
    }


    public void clkplay(AudioManager inputMusic){
        music.start();
        inputMusic.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE,0);

    }
    public void clkpause(AudioManager inputMusic){
        inputMusic.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE,0);
    }
}
