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
    private Button btn1;
    private VideoView videoView;
    private Uri ur;
    private int curLevel,numOfWin=0,numOfLose=0 , tmpLevel;
    private final int DISAPPEAR_OBJECTS=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        videoView= findViewById(R.id.videoView);
        btn1 = findViewById(R.id.button);

        /** for the background video**/

        ur= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.underwater1);
        videoView.setVideoURI(ur);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        /** **/

        game = new GameView(this);

        ConstraintLayout layout = findViewById(R.id.layout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        layout.addView(game, width - 1, height - 1);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print("level= "+video.level);
                video.level++;
                print(video.level + "");
                if (video.level > 9)
                    video.level = 1;
            }
        });
            print("main level= "+video.level);
            gameProcess();

//        timer();


    }
    public void showAlertDialogButtonClicked(View view) {

        LayoutInflater inflater=this.getLayoutInflater();
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 =inflater.inflate(R.layout.activity_answers,null);
        builder.setView(view1).setTitle("תשובות");

//        print("dialog level= "+video.level);

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
                which=tmpLevel;
                switch (which) {
                    case 1:
                        if (which==tmpLevel){
                            print("Well done!\uD83C\uDF89\uD83C\uDF89");
                            numOfWin++;
                        }
                        else{
                            print(which+" "+curLevel+" 11111");
                        }
                        break;

                    case 2:
                        if (which==tmpLevel){
                            print("Well done!\uD83C\uDF89\uD83C\uDF89");
                            numOfWin++;
                        }
                    else
                        print(video.level+" "+curLevel);
                        break;
                    case 3:
                        if (which==tmpLevel){
                            print("Well done!\uD83C\uDF89\uD83C\uDF89");
                            numOfWin++;
                        }
                        else
                            print(which+"");
                        break;
                    case 4:
                        if (which==tmpLevel){
                            print("Well done!\uD83C\uDF89\uD83C\uDF89");
                            numOfWin++;
                        }
                        else
                            print(which+"");
                        break;
                    case 5:
                        if (which==tmpLevel){
                            print("Well done!\uD83C\uDF89\uD83C\uDF89");
                            numOfWin++;
                        }
                        else
                            print(which+"");
                        break;
                    case 6:
                        if (which==tmpLevel){
                            print("Well done!\uD83C\uDF89\uD83C\uDF89");
                            numOfWin++;
                        }
                        else
                            print(which+"");
                        break;
                    case 7:
                        if (which==tmpLevel){
                            print("Well done!\uD83C\uDF89\uD83C\uDF89");
                            numOfWin++;
                        }
                        else
                            print(which+"");
                        break;
                    case 8:
                        if (which==tmpLevel){
                            print("Well done!\uD83C\uDF89\uD83C\uDF89");
                            numOfWin++;
                        }
                        else
                            print(which+"");
                        break;
                    case 9:
                        if (which==tmpLevel){
                            print("Well done!\uD83C\uDF89\uD83C\uDF89");
                            numOfWin++;
                        }
                        else
                            print(which+"");
                        break;
                }
                video.level=curLevel;
                if (numOfWin==3){
                    video.level++;
                    print("vid level= "+video.level);
                    numOfWin=0;
                    if (video.level==10){}////end of the game.
                }

                gameProcess();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    public void AnswersDialog(){
//        Answers answers=new Answers();
//        answers.show(getSupportFragmentManager(),"answers dialog");
//    }

    private void print(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_LONG).show();
    }

    private void timer(){
//        print("timer level= "+video.level);
        final Handler handler = new Handler();
        final int delay = 3000; //milliseconds
        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
//                curLevel=video.level;
                video.level=DISAPPEAR_OBJECTS;
                showAlertDialogButtonClicked(game);
//                handler.postDelayed(this, delay);
            }
        }, delay);

    }

    public void gameProcess(){
//        print("game lvel= "+video.level);
        if (video.level<=2){
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
                print("rnd= "+rnd);
                video.level=rnd;
                tmpLevel=rnd;
                timer();
            }

        }
    }


}
