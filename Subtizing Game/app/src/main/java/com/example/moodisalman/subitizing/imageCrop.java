package com.example.moodisalman.subitizing;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;

/**
 * This activity used when the user want to add a photo as an object , either of the camera or photo gallery.
 * **/

public class imageCrop extends AppCompatActivity {

    private Button btnAdd ,btnDone;
    private ImageView mImg;
    private Uri mImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);

        btnAdd=findViewById(R.id.btnAddImage);
        btnDone=findViewById(R.id.btnDoneCrop);
        mImg=findViewById(R.id.imageView2);

        btnDone.setEnabled(false);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChooseFile();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Levels.class);
                startActivity(i);
            }
        });
    }

    private void onChooseFile(){//the func will open a new activity the choose an image
        CropImage.activity().start(imageCrop.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // all the data after choosing an image will be passed to this func.
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result=CropImage.getActivityResult(data);

            if (resultCode==RESULT_OK){
                mImgUri=result.getUri();
                mImg.setImageURI(mImgUri);

                try {// here where the image put as an object of the game
                    GameView.obj= MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImgUri);
                    btnDone.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if (resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){

                Exception e=result.getError();
                Toast.makeText(this,"Error: "+e,Toast.LENGTH_SHORT).show();
            }
        }


    }
}
