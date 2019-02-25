package com.example.moodisalman.subitizing;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.VideoView;


public class MyService extends Service
{
    //creating a mediaplayer object
    private MediaPlayer player;
    private boolean isRunning;
    private Uri ur;
//    Activity activity = WindowUtil.scanForActivity(MainActivity.ge);
//    private String path = "android.resource://" + getPackageName() + "/" + R.raw.underwater1;

//    public MyService(){
//
//    }

//    public void init(Uri uri){
//        m=new MainActivity();
//        videoBG=m.findViewById(R.id.videoView);
////        Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.underwater1);
//        videoBG.setVideoURI(uri);
//
//
////        String path = "android.resource://" + getPackageName() + "/" + R.raw.underwater1;
////        videoBG.setVideoURI(Uri.parse(path));
//        videoBG.start();
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        ur= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.underwater1);
        video.v[0].setVideoURI(ur);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {


        video.v[0].start();


        video.v[0].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        //staring the player

        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

        //stopping the player when service is destroyed
        //video.v[0].pause();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
