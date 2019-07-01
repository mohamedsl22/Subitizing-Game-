package com.example.moodisalman.subitizingadmin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

/**
 * This activity used to add users to the dataBase (fireBase) only by the admin,
 * the admin must insert user name , and Id , and Age . **/


public class AddUser extends AppCompatActivity {

    private EditText edtName , edtID , edtAge;
    private Button btnAdd;
    private DatabaseReference dbUser;
    private FirebaseAuth mAuth;
    private Query idQuery;
    static String Amail,Apass;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mAuth=FirebaseAuth.getInstance();

        edtID=findViewById(R.id.edtID);
        edtName=findViewById(R.id.edtUserName);
        edtAge=findViewById(R.id.edtAge);
        btnAdd=findViewById(R.id.btnFinalAdd);
        progressBar=findViewById(R.id.progressBar);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser();
            }
        });
    }

    private void AddUser(){// to add a new user to the firebase dataBase

         final String name=edtName.getText().toString().trim();
         final String Id=edtID.getText().toString().trim();
         final String age=edtAge.getText().toString().trim();
         final String mail=Id+"@g.com";
         final String pass="123456";
        if (!TextUtils.isEmpty(name)){
            if (!TextUtils.isEmpty(Id)){
                if (!TextUtils.isEmpty(age)){
                    progressBar.setVisibility(View.VISIBLE);
                    idQuery=FirebaseDatabase.getInstance().getReference().child("users").orderByChild("id")
                            .equalTo(Id); // used to check if the user is already registered

                    idQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getChildrenCount()>0){ // means the user found in the db
                                Toasty.error(AddUser.this, "User already registered.",
                                        Toast.LENGTH_SHORT, true).show();
                                progressBar.setVisibility(View.GONE);
                            }
                            else{
                                mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(AddUser.this,
                                        new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if (!task.isSuccessful()){
                                                    print("Adding Error");
                                                }
                                                else {
                                                    String user_id=mAuth.getCurrentUser().getUid();
                                                    dbUser= FirebaseDatabase.getInstance().getReference().child("users")
                                                            .child(user_id);
                                                    User user = new User(name, age, Id , user_id);
                                                    dbUser.setValue(user);
                                                    mAuth.signInWithEmailAndPassword(Amail,Apass).addOnCompleteListener(AddUser.this, new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            progressBar.setVisibility(View.GONE);
                                                            if (!task.isSuccessful()){
                                                                print("SignIn Error " + mail+" "+pass);
                                                            }
                                                            else
                                                                Toasty.success(AddUser.this, "User Added Successfully.",
                                                                        Toast.LENGTH_SHORT, true).show();

                                                        }
                                                    });

                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }else
                    print("You must enter a user Age.");

            }else
                print("You must enter a user ID.");
        }else
            print("You must enter a user name.");

    }

    private void print(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}
