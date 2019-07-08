package com.example.moodisalman.subitizingadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

/**
This activity made for to let the admin add notes to the user.**/

public class AddNote extends AppCompatActivity {
    private Button btnAdd;
    private EditText noteText;
    private TextView name;
    private String userName, userID,uid;
    private DatabaseReference dbNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        btnAdd=findViewById(R.id.btnAddNote);
        noteText=findViewById(R.id.edtNote);
        name=findViewById(R.id.txtNameNote);

        Intent intent=getIntent();
        userName=intent.getStringExtra("name");
        userID=intent.getStringExtra("userID");
        uid=intent.getStringExtra("id");

        name.setText(userName);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });


    }

    private void addNote(){//where it get the note and send it to database
        final String note=noteText.getText().toString();
        dbNotes= FirebaseDatabase.getInstance().getReference("notes").child(userID);
        if (!TextUtils.isEmpty(note)){
            String noteID=dbNotes.push().getKey();
            Notes newNote=new Notes(note,getDateTime(),false,userName,uid);
            dbNotes.child(noteID).setValue(newNote).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toasty.success(getApplicationContext(),"Note has been added successfully",
                            Toast.LENGTH_SHORT,true).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toasty.error(AddNote.this, "error, check your internet connection",
                            Toast.LENGTH_SHORT, true).show();
                }
            });
        }
        else{
            print("You must enter a note.");
        }

    }

    private void print(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    private String getDateTime() {// to return the date of the current day.
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
