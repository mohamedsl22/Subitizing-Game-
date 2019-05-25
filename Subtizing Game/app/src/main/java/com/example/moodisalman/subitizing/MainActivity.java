package com.example.moodisalman.subitizing;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements loginDialog.LoginDialogListener {
    private ImageView plybtn;
    private Intent i ;
    private GifImageView nameGif;
    private TextView versiontxt;
    private Dialog dialog;
    private dbManager dbMngr;
    private FirebaseAuth.AuthStateListener authStateListener;


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


        dialog = new Dialog(this);
        dbMngr=new dbManager();
        dbManager.userID=null;


        plybtn.setOnClickListener(new View.OnClickListener() {//if the play button was clicked
            @Override
            public void onClick(View view) {

                openDialog();
            }
        });


    }


    public void openDialog(){//the login dialog
        loginDialog loginDialog=new loginDialog();
        loginDialog.show(getSupportFragmentManager(),"login dialog");
    }


    @Override
    public void applyText(String id) {//the text is brought from loginDialog.java (LoginDialogListener).
        // what I want to do with the id.



        final String mail=id+"@g.com";
        final String pass="123456";

        dbManager.mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    print("SignIn Error ");
                    openDialog();
                }
                else{
                    dbManager.userID=dbManager.mAuth.getCurrentUser().getUid();
                    dbManager.initDbResult();
                    FirebaseDatabase.getInstance().getReference().child("users").child(dbManager.userID).addValueEventListener(
                            new ValueEventListener() {//to reach the id name.
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    print("welcome "+dataSnapshot.child("username").getValue().toString());
                                    i = new Intent(MainActivity.this, Levels.class);
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



    private void print(String s ){
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }



}
