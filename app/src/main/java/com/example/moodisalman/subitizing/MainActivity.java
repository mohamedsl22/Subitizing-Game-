package com.example.moodisalman.subitizing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView plybtn;
    private Intent i ;
    private GifImageView nameGif;
    private TextView versiontxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameGif=findViewById(R.id.gifViewName);
        plybtn=findViewById(R.id.plyid);

        versiontxt=findViewById(R.id.txtver);
        versiontxt.setText("version: "+BuildConfig.VERSION_NAME); // to show the version of the game

        try {// to play the gif image

            InputStream inputStream=getAssets().open("fly.gif");
            byte[] bytes= IOUtils.toByteArray(inputStream);
            nameGif.setBytes(bytes);
            nameGif.startAnimation();

        }catch (IOException ex){
            Toast.makeText(this, "Gif play erro",
                    Toast.LENGTH_SHORT).show();
        }





        plybtn.setOnClickListener(new View.OnClickListener() {//if the play button was clicked
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Levels.class);
                startActivity(i);
            }
        });


    }



}
