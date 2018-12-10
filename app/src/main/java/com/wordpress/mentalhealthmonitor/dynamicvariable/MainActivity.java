package com.wordpress.mentalhealthmonitor.dynamicvariable;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView facePic;
    private ImageButton takePic;
    private Button toGame;
    private Button toSettingsBut;
    private Button toInstructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facePic = findViewById(R.id.imageView2);
        takePic = findViewById(R.id.takePic);
        toGame = findViewById(R.id.toGameButton);
        toSettingsBut = findViewById(R.id.toSettings);
        toInstructions = findViewById(R.id.toInstructoionsButtton);



        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        toGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
        toSettingsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });
        toInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstructions();
            }
        });


    }
    //This is a comment
    public void openGame(){
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
    public void openSettings(){
        Intent intent = new Intent(this, MySettings.class);
        startActivity(intent);
    }
    public void openInstructions(){
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);

    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            onActivityResult(REQUEST_IMAGE_CAPTURE,RESULT_OK,cameraIntent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        facePic.setImageBitmap(bitmap);
                    }

                }
                break;
        }
    }
}
