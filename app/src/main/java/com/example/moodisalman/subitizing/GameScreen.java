package com.example.moodisalman.subitizing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
import com.felipecsl.gifimageview.library.GifImageView;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;


/**
 * so how the game works?
 * the game has 6 levels, each objNum has four num of objects, so let's say objNum 1 -> it has from 1 to 4
 * and objNum 2 -> from 2 to 5, so basically from the objNum num to objNum num+3 ,
 * in each objNum the player must success in 16 repeats if not then the repeats will increase automatically,
 * also in each objNum the time will decrease with each success or increase with each lose , and will return to what it
 * was in the next objNum.
 */

public class GameScreen extends AppCompatActivity {
    private GameView game;
    private VideoView videoView;
    private Uri ur;
    private Button btn1,btn2,btn3,btn4;
    private GifImageView gif;
    private  Dialog dialog;
    private MediaPlayer rightSound , wrongSound;
    /**
     * tmpLevel=also saves the current gameData.objNum temprarly (objNum=how many objects, used in dialog)
     * curLevWin=saves num of wins in the current objNum
     */
    private int tmpLevel,millesecDiffrence=0, curLevWin=0;
    private final int DISAPPEAR_OBJECTS=-1 ;
    private final int THIRTY_MILLISEC=30;
    private final int REGULAR_MODE=0,RANDOM_MODE=1;
    private final int NUM_OF_WANTED_WINS=44;
    public static int numOfWin,numOfLose;



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

        /** Dialog **/
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_answers);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        /** **/

        rightSound=MediaPlayer.create(this , R.raw.correct);
        wrongSound=MediaPlayer.create(this , R.raw.trya);


        numOfWin=0;
        numOfLose=0;
        gameData.objNum =0;
        gameData.repeats=0;
        gameData.gameMode=REGULAR_MODE;
        gameData.howManyApprnce=new int[9];

    }


    private void print(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    private void timer(){//delayed time until showing the the dialog

        final Handler handler = new Handler();
        final int delay = 1500; //milliseconds
        handler.postDelayed(new Runnable(){
            public void run(){
                //do exampleing
                gameData.objNum =DISAPPEAR_OBJECTS;
                AnswersCustomAlertDialog();
            }
        }, delay-millesecDiffrence);

    }

    public void gameProcess(){//game rules

            if (gameData.repeats==0){//means at the first of the objNum to show the new add
                if (gameData.outLevel!=1){
                    for (int i=0;i<9;i++)
                        gameData.howManyApprnce[i]=0;
                }
                if (gameData.outLevel==1)
                    gameData.objNum = gameData.outLevel;
                else
                 gameData.objNum = gameData.outLevel+3;
            }
            else{
                while(true){
                    gameData.objNum =(int)(Math.random()*((gameData.outLevel+3)- gameData.outLevel+1))+ gameData.outLevel;
                    if (gameData.outLevel<4 && gameData.gameMode==REGULAR_MODE){//to ensure that objNum appear only twice in reg mode
                      if (gameData.howManyApprnce[gameData.objNum-1]<11)
                           break;
                    }
                    else{//if randam mode, or reg mode above lev 3 (appears 4 times )
                        if (gameData.howManyApprnce[gameData.objNum-1]<22)
                            break;

                    }

                }

            }
            tmpLevel= gameData.objNum;

            if (curLevWin>=NUM_OF_WANTED_WINS && gameData.outLevel<=3)
                initArraysRandomly();


            gameData.repeats++;
            timer();
    }

    private void incaseOfLose(){// what to do incase of lose.
        wrongSound.start();

        numOfLose++;
        gameData.repeats++;
        if (numOfLose%3==0)
            millesecDiffrence-=THIRTY_MILLISEC;
        if (numOfLose%5==0){
            print("\uD83D\uDE14");
        }
        new Handler().postDelayed(new Runnable() {// wait 3 sec and then do inside of run() methods
            @Override
            public void run() {
                gameProcess();
            }
        },900);


    }

    private void incaseOfWin(){//what to do incase of win
        rightSound.start();
        gameData.repeats++;
        numOfWin++;
        curLevWin++;
        gameData.howManyApprnce[tmpLevel-1]++;
        if (curLevWin==NUM_OF_WANTED_WINS && gameData.outLevel<=3)
            gameData.gameMode=RANDOM_MODE;

        if (curLevWin%5==0)
            millesecDiffrence+=THIRTY_MILLISEC;



        if (numOfWin!=0 && numOfWin%(NUM_OF_WANTED_WINS*2)==0){
            print("יפה מאוד! \uD83C\uDF89\uD83D\uDE01\uD83C\uDF89");
            gameData.outLevel++;
            gameData.repeats=0;
            millesecDiffrence=0;
            curLevWin=0;
            gameData.gameMode=REGULAR_MODE;
            if (gameData.outLevel>6){
                gameData.outLevel=1;
                print("You have finished all the levels");
            }
        }

        positiveFeedback();
    }

    private void initArraysRandomly(){//init the random x,y of the objects in random mode
        gameData.xArr=new int[tmpLevel];
        gameData.yArr=new int[tmpLevel];

        int x,y;

        for (int i=0;i<tmpLevel;i++){
            x=(int)(Math.random()*5);
            y=(int)(Math.random()*4)+1;

            if (i==0){//means that there is no need to check if there are same coordinates in arrays
                gameData.xArr[i]=x;
                gameData.yArr[i]=y;
            }
            else{// to make sure that there is no same coordinates in the arrays

                for (int j=0;j<i;j++){
                 if (gameData.xArr[j]==x && gameData.yArr[j]==y){// if found
                     i--;
                     break;//return to the first loop and change x,y
                 }
                 if (j+1==i){//if not
                     gameData.xArr[i]=x;
                     gameData.yArr[i]=y;
                 }

                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


        if(dialog.isShowing()){
            dialog.dismiss();
        }



/** for the background gameData and playing the game**/

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

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    finish();
                    dialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameScreen.this);
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setMessage("בטוח שרוצה לצאת?")
                            .setCancelable(false)
                            .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent play =new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(play);
                                }
                            })
                            .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
//                                    finish();
                                    gameProcess();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                return true;
            }
        });

        gameProcess();

    }

    public void AnswersCustomAlertDialog(){// the dialog of the answers.


        btn1=dialog.findViewById(R.id.button);
        btn2=dialog.findViewById(R.id.button2);
        btn3=dialog.findViewById(R.id.button3);
        btn4=dialog.findViewById(R.id.button4);

        dialog.setCancelable(false);



        /** to put answers in the buttons **/
        if (tmpLevel==1){
            btn1.setText("1");
            btn2.setText("2");
            btn3.setText("3");
            btn4.setText("4");
        }
        else if(tmpLevel>=2 && tmpLevel<=5){
            btn1.setText("2");
            btn2.setText("3");
            btn3.setText("4");
            btn4.setText("5");
        }
        else {
            btn1.setText("6");
            btn2.setText("7");
            btn3.setText("8");
            btn4.setText("9");
        }
        dialog.show();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(btn1.getText().toString());
                dialogHelpMethod(dialog,i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(btn2.getText().toString());
                 dialogHelpMethod(dialog,i);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(btn3.getText().toString());
                dialogHelpMethod(dialog,i);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(btn4.getText().toString());
                dialogHelpMethod(dialog,i);
            }
        });

    }


    public void positiveFeedback(){//to show the positive feedback
        final Dialog animtDialog = new Dialog(this);
        animtDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        animtDialog.setContentView(R.layout.positivefb);
        animtDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        gif=animtDialog.findViewById(R.id.gifView);


        animtDialog.setCancelable(false);
        animtDialog.show();

        try {
            InputStream inputStream=getAssets().open("feedbackgif.gif");
            byte[] bytes= IOUtils.toByteArray(inputStream);
            gif.setBytes(bytes);
            gif.startAnimation();

        }catch (IOException ex){
            print("feedback gif error");
        }

        new Handler().postDelayed(new Runnable() {// wait 3 sec and then do inside of run() methods
            @Override
            public void run() {
                animtDialog.dismiss();
                gif.stopAnimation();
                gameProcess();
            }
        },2000);


    }

    private void dialogHelpMethod(Dialog d, int i){//if pressed any button in the dialog
        d.dismiss();

        if (i==tmpLevel)
            incaseOfWin();
        else
            incaseOfLose();
    }




//    public void onBackPressed() {
//        if(dialog.isShowing()){
//            dialog.dismiss();
//        }
////
////        AlertDialog.Builder builder = new AlertDialog.Builder(this);
////        builder.setTitle(R.string.app_name);
////        builder.setIcon(R.mipmap.ic_launcher);
////        builder.setMessage("בטוח שרוצה לצאת?")
////                .setCancelable(false)
////                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////
////                                Intent play =new Intent(getApplicationContext(), MainActivity.class);
////                        startActivity(play);
////                    }
////                })
////                .setNegativeButton("No", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        finish();
////                    }
////                });
////        AlertDialog alert = builder.create();
////        alert.show();
//
//
//
//
//    }

}
