package com.example.moodisalman.subitizing;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.BuildConfig;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity{
    private Intent i ;
    private GifImageView nameGif , nameGif2;
    private TextView versiontxt;
    private Button btnLogin , btnGuest;
    private EditText edtID;
    private dbManager dbMngr;
    private ProgressBar pr;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameGif=findViewById(R.id.gifViewName);
        nameGif2=findViewById(R.id.gifViewName1);

        btnGuest=findViewById(R.id.btnGuest);
        btnLogin=findViewById(R.id.btnLogIn);
        edtID=findViewById(R.id.edtIDText);

        pr=findViewById(R.id.progressBar);
        pr.setVisibility(View.GONE);

        versiontxt=findViewById(R.id.txtver);
        versiontxt.setText("version: "+ BuildConfig.VERSION_NAME); // to show the version of the game

        try {// to play the gif image

            InputStream inputStream=getAssets().open("stars_animation.gif");
            byte[] bytes= IOUtils.toByteArray(inputStream);
            nameGif.setBytes(bytes);
            nameGif2.setBytes(bytes);

        }catch (IOException ex){
            Toast.makeText(this, "Gif play error",
                    Toast.LENGTH_SHORT).show();
        }


        dbMngr=new dbManager();
        dbManager.userID=null;


        btnLogin.setEnabled(false);

        edtID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnLogin.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtID.getText().toString().trim().length() == 0)
                    btnLogin.setEnabled(false);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=edtID.getText().toString().trim();
                applyText(id);
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, choosegame.class);
                startActivity(i);
            }
        });



    }




    public void applyText(String id) {//the text is brought from loginDialog.java (LoginDialogListener).
        // what I want to do with the id.

        final String mail=id+"@g.com";
        final String pass="123456";

        dbManager.mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    print("SignIn Error ");
                }
                else{
                    pr.setVisibility(View.VISIBLE);
                    pr.setIndeterminate(true);
                    dbManager.userID=dbManager.mAuth.getCurrentUser().getUid();
                    dbManager.initDbResult();
                    FirebaseDatabase.getInstance().getReference().child("users").child(dbManager.userID).addValueEventListener(
                            new ValueEventListener() {//to reach the id name.
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    print("welcome "+dataSnapshot.child("username").getValue().toString());
                                    i = new Intent(MainActivity.this, choosegame.class);
                                    pr.setVisibility(View.GONE);
                                    startActivity(i);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            }
                    );


                }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        nameGif.stopAnimation();
        nameGif2.stopAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        nameGif.startAnimation();
        nameGif2.startAnimation();
    }

    private void print(String s ){
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }



}
