package com.example.moodisalman.subitizing;

import android.content.DialogInterface;
import android.content.Intent;
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

public class GameScreen extends AppCompatActivity {
    private GameView game;
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        video.v[0] = findViewById(R.id.videoView);
        btn1 = findViewById(R.id.button);

//        if (video.isRunning == false) {
//            video.isRunning = true;
//            startService(new Intent(this, MyService.class));
//
//
//        }
        game = new GameView(this);

        print(video.level + "");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("debuging", "level = " + video.level);
                AnswersDialog();
                video.level++;
                print(video.level + "");
                if (video.level >= 9)
                    video.level = 1;
            }
        });


        ConstraintLayout layout = findViewById(R.id.layout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        layout.addView(game, width - 1, height - 1);


//        new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
////                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
//                AnswersDialog();
//                video.level++;
//                if(video.level>=9)
//                    video.level=0;
//
//            }
//
//            public void onFinish() {
////                mTextField.setText("done!");
//            }
//        }.start();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showAlertDialogButtonClicked(game);
//
//                }
//
//            }, 3000);
            timer();
//        final Handler handler = new Handler();
//        final int delay = 3000; //milliseconds
//
//        handler.postDelayed(new Runnable(){
//            public void run(){
//                //do something
//                showAlertDialogButtonClicked(game);
//                handler.postDelayed(this, delay);
//            }
//        }, delay);
//            video.level++;
//            if (video.level >= 9)
//                video.level = 1;

    }
    public void showAlertDialogButtonClicked(View view) {

        LayoutInflater inflater=this.getLayoutInflater();
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 =inflater.inflate(R.layout.activity_answers,null);
        builder.setView(view1).setTitle("תשובות");

        String[] tmpAns=new String[4];
        if (video.level<=4){
            for (int i=0;i<tmpAns.length;i++)
                tmpAns[i]=i+1+"";
        }
        else{
            for (int i=0;i<tmpAns.length;i++){
                tmpAns[i]=video.level-i+"";
            }
        }

        // add a list
        builder.setItems(tmpAns, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                which++;
                switch (which) {
                    case 1:
                        if (which==video.level)
                            print("Well done!");
                        else
                            print(which+" "+video.level+" 11111");
                        timer();
                        break;
                    case 2:
                        if (which==video.level)
                        print("Well done!");
                    else
                        print(which+"");
                    timer();
                        break;
                    case 3:
                        if (which==video.level)
                            print("Well done!");
                        else
                            print(which+"");
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void AnswersDialog(){
        Answers answers=new Answers();
        answers.show(getSupportFragmentManager(),"answers dialog");
    }

    private void print(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_LONG).show();
    }

    private void timer(){
        final Handler handler = new Handler();
        final int delay = 3000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                showAlertDialogButtonClicked(game);
//                handler.postDelayed(this, delay);
            }
        }, delay);
    }

}
