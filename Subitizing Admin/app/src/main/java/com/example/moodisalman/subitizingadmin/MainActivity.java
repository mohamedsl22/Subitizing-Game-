package com.example.moodisalman.subitizingadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This activity is for the main activity shown after the login**/

public class MainActivity extends AppCompatActivity implements loginDialog.LoginDialogListener {
    private CardView crdAdd,crdList,crdNotes,crdSgnOut;
    private Intent i;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();



        openDialog();

        crdAdd=findViewById(R.id.btnAddUser);
        crdList=findViewById(R.id.btnList);
        crdNotes=findViewById(R.id.btnNote);
        crdSgnOut=findViewById(R.id.btnSignout);

        DatabaseReference dbNote= FirebaseDatabase.getInstance().getReference("notes");


        crdAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), AddUser.class);
                startActivity(i);
            }
        });

        crdList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), UsersList.class);
                startActivity(i);
            }
        });

        crdNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), NotesList.class);
                startActivity(i);
            }
        });

        crdSgnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                print("Signed out");
                openDialog();
            }
        });



    }

    public void openDialog(){//the login dialog
        loginDialog loginDialog=new loginDialog();
        loginDialog.setCancelable(false);
        loginDialog.show(getSupportFragmentManager(),"login dialog");
    }



    @Override
    public void applyText(final String mail, final String pass) {
        // what I want to do with the id.

        try{

        AddUser.Amail=mail;
        AddUser.Apass=pass;
        mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    print("SignIn Error " + mail+" "+pass);
                    openDialog();
                }

            }
        });} catch (Exception e ){
            print("You must type mail and password");
            openDialog();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void print(String s ){
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }
}
