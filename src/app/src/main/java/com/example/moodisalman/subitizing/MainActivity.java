package com.example.moodisalman.subitizing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private GameView game;
    private LinearLayout myMovieslay ,watchLay,rateLay,locationLay;
    private Intent i ;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myMovieslay=findViewById(R.id.myMovieLayout);
        watchLay=findViewById(R.id.watchLayout);
        rateLay=findViewById(R.id.RatedLay);
        locationLay=findViewById(R.id.locationLay);






//        if (video.isRunning==false){
//            video.isRunning=true;
//            startService(new Intent(this, MyService.class));
//        }

//        startService(new Intent(this, MyService.class));

        myMovieslay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
            }
        });


    }


    protected void onDestroy() {

        super.onDestroy();
        stopService(new Intent(this, MyService.class));
    }
}
