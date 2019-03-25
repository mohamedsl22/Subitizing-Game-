package com.example.moodisalman.subitizing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.felipecsl.gifimageview.library.GifImageView;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView plybtn;
    private Intent i ;
    private GifImageView nameGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameGif=findViewById(R.id.gifViewName);
        plybtn=findViewById(R.id.plyid);

        try {
            InputStream inputStream=getAssets().open("fly.gif");
            byte[] bytes= IOUtils.toByteArray(inputStream);
            nameGif.setBytes(bytes);
            nameGif.startAnimation();

        }catch (IOException ex){
        }





        plybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Levels.class);
                startActivity(i);
            }
        });


    }



}
