package com.example.moodisalman.subitizing;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Levels extends AppCompatActivity implements View.OnClickListener {
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btnG,btnR;
    private Intent i;
    private Dialog dialog;
    private final int REGULAR_MODE=0,RANDOM_MODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        btn1=findViewById(R.id.button5);
        btn2=findViewById(R.id.button6);
        btn3=findViewById(R.id.button7);
        btn4=findViewById(R.id.button8);
        btn5=findViewById(R.id.button9);
        btn6=findViewById(R.id.button10);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sub_level);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button5:
                gameData.outLevel=1;
                AnswersCustomAlertDialog();
                break;
            case R.id.button6:
                gameData.outLevel=2;
                AnswersCustomAlertDialog();
                break;
            case R.id.button7:
                gameData.outLevel=3;
                AnswersCustomAlertDialog();
                break;
            case R.id.button8:
                gameData.outLevel=4;
                gameData.gameMode=REGULAR_MODE;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
                break;
            case R.id.button9:
                gameData.outLevel=5;
                gameData.gameMode=REGULAR_MODE;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
                break;
            case R.id.button10:
                gameData.outLevel=6;
                gameData.gameMode=REGULAR_MODE;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
                break;
        }

    }

    public void AnswersCustomAlertDialog(){// the dialog of the answers.

        btnG=dialog.findViewById(R.id.button11);
        btnR=dialog.findViewById(R.id.button12);

//        dialog.setCancelable(false);


        dialog.show();

        btnG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.gameMode=REGULAR_MODE;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
            }
        });
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.gameMode=RANDOM_MODE;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
            }
        });


    }
}
