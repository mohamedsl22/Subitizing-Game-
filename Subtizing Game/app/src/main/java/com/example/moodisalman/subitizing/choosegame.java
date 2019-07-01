package com.example.moodisalman.subitizing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

/**This activity is used for letting the player choose a game world (theme) **/

public class choosegame extends AppCompatActivity {
    private CardView btnfish, btnstars , btnsnow,btnFall , btnAddImage,btnFirePlace;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosegame);

        btnfish=findViewById(R.id.btnfishgame);
        btnstars=findViewById(R.id.btnstarsgame);
        btnsnow=findViewById(R.id.btnsnow);
        btnFall=findViewById(R.id.btnsfall);
        btnFirePlace=findViewById(R.id.btnfirePlace);
        btnAddImage=findViewById(R.id.btnAddPersonalImage);

        btnfish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.chosenGame=1;
                i = new Intent(getApplicationContext(), Levels.class);
                startActivity(i);

            }
        });

        btnstars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.chosenGame=2;
                i = new Intent(getApplicationContext(), Levels.class);
                startActivity(i);
            }
        });

        btnsnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.chosenGame=3;
                i = new Intent(getApplicationContext(), Levels.class);
                startActivity(i);
            }
        });

        btnFall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.chosenGame=4;
                i = new Intent(getApplicationContext(), Levels.class);
                startActivity(i);
            }
        });

        btnFirePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.chosenGame=6;
                i = new Intent(getApplicationContext(), Levels.class);
                startActivity(i);
            }
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.chosenGame=5;
                i = new Intent(getApplicationContext(), imageCrop.class);
                startActivity(i);
            }
        });
    }
}
