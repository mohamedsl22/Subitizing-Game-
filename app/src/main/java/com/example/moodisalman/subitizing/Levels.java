package com.example.moodisalman.subitizing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Levels extends AppCompatActivity implements View.OnClickListener {
    private Button btn1,btn2,btn3,btn4,btn5,btn6;
    private Intent i;

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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button5:
                gameData.outLevel=1;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
                break;
            case R.id.button6:
                gameData.outLevel=2;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
                break;
            case R.id.button7:
                gameData.outLevel=3;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
                break;
            case R.id.button8:
                gameData.outLevel=4;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
                break;
            case R.id.button9:
                gameData.outLevel=5;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
                break;
            case R.id.button10:
                gameData.outLevel=6;
                i = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(i);
                break;
        }

    }
}
