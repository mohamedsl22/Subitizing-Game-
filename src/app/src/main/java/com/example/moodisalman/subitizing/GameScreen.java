package com.example.moodisalman.subitizing;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class GameScreen extends AppCompatActivity {
    private GameView game;
    private VideoView videoView;
    private Uri ur;
    private int curLevel,numOfWin=0,numOfLose=0 ,tmpLevel,millesecDiffrence=0;
    private final int DISAPPEAR_OBJECTS=-1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        videoView= findViewById(R.id.videoView);


        /** for the game layout **/
        game = new GameView(this);
        ConstraintLayout layout = findViewById(R.id.layout);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        layout.addView(game, width - 1, height - 1);
        /** **/


        /** for the background video and playing the game**/
        video.level=0;
        ur= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.underwater1);
        videoView.setVideoURI(ur);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                video.level=1;
                gameProcess();
            }
        });
        /** **/



    }
    public void showAlertDialogButtonClicked(View view) {

        LayoutInflater inflater=this.getLayoutInflater();
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 =inflater.inflate(R.layout.activity_answers,null);
        builder.setView(view1).setTitle("תשובות");

//        print("dialog level= "+video.level);
        /** to put answers in the buttons **/
        String[] tmpAns=new String[4];
        if (tmpLevel==1){
            for (int i=0;i<tmpAns.length;i++)
                tmpAns[i]=i+1+"";
        }
        else if(tmpLevel>=2 && tmpLevel<=5){
            for (int i=0;i<tmpAns.length;i++)
                tmpAns[i]=i+2+"";
        }
        else {
            for (int i=0;i<tmpAns.length;i++)
                tmpAns[i]=i+6+"";
        }

        // add a list
        builder.setItems(tmpAns, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (tmpLevel==1)
                    which++;
                 else if (tmpLevel<=5)
                     which+=2;
                 else
                     which+=6;


                switch (which) {
                    case 1:
                        if (which==tmpLevel)
                            incaseOfWin();

                        else
                            incaseOfLose();

                        break;
                    case 2:
                        if (which==tmpLevel)
                            incaseOfWin();

                        else
                            incaseOfLose();

                        break;
                    case 3:
                        if (which==tmpLevel)
                            incaseOfWin();

                        else
                            incaseOfLose();

                        break;
                    case 4:
                        if (which==tmpLevel)
                            incaseOfWin();

                        else
                            incaseOfLose();

                        break;
                    case 5:
                        if (which==tmpLevel)
                            incaseOfWin();

                        else
                            incaseOfLose();

                        break;
                    case 6:
                        if (which==tmpLevel)
                            incaseOfWin();

                        else
                            incaseOfLose();

                        break;
                    case 7:
                        if (which==tmpLevel)
                            incaseOfWin();

                        else
                            incaseOfLose();

                        break;
                    case 8:
                        if (which==tmpLevel)
                            incaseOfWin();

                        else
                            incaseOfLose();

                        break;
                    case 9:
                        if (which==tmpLevel)
                            incaseOfWin();

                        else
                            incaseOfLose();

                        break;
                }
                video.level=curLevel;
                if (numOfWin==3){
                    video.level++;
                    numOfWin=0;
                    if (video.level==10){
                        video.level=1;
                    }////end of the nine levels .
                }

                gameProcess();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void print(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    private void timer(){//delayed time until showing the the dailog

        final Handler handler = new Handler();
        final int delay = 1500; //milliseconds
        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                video.level=DISAPPEAR_OBJECTS;
                showAlertDialogButtonClicked(game);
            }
        }, delay-millesecDiffrence);

    }

    public void gameProcess(){//game rules
        if (video.level==1){
            tmpLevel=video.level;
            curLevel= video.level;
            timer();
        }
        else{
            if (numOfWin==0){
                curLevel=video.level;
                tmpLevel=video.level;
                timer();
            }
            else{
                curLevel=video.level;//to choose a level randomly and save main level in tmplevel
                int rnd=(int)(Math.random()*video.level)+1;
//                print("rnd= "+rnd);
                video.level=rnd;
                tmpLevel=rnd;
                timer();
            }

        }
    }

    private void incaseOfLose(){
        numOfLose++;
        if (numOfLose%2==0)
            millesecDiffrence-=30;
        if (numOfLose%5==0){
            print("\uD83D\uDE14");
        }
    }

    private void incaseOfWin(){
        print("יפה מאוד! \uD83C\uDF89\uD83D\uDE01\uD83C\uDF89");
        numOfWin++;
        millesecDiffrence+=30;
    }


}
