package com.example.moodisalman.subitizing;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**This activity is used for letting the player choose a game world (theme) **/

public class choosegame extends AppCompatActivity {
    private CardView btnfish, btnstars , btnsnow,btnFall , btnAddImage,btnFirePlace,btnRose;
    private Intent i;
    private FloatingActionButton fab;
    private ListView listView;
    private List<Notes> listNotes;
    private NotesAdapter adapter;
    private MediaPlayer notificationSound;

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
        btnRose=findViewById(R.id.btnRedRose);
        fab = findViewById(R.id.fab);
        listView=new ListView(this);
        notificationSound=MediaPlayer.create(this, R.raw.notification_sound);


        if (dbManager.userID!=null){

            if(dbManager.ifMessages){
               fab.setImageResource(R.drawable.ic_message_black_24dp);
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                notificationSound.start();
          }
        }
        else
            fab.setVisibility(View.INVISIBLE);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessagesDialog();

            }
        });


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

        btnRose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.chosenGame=7;
                i = new Intent(getApplicationContext(), Levels.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (dbManager.userID!=null){
            listNotes=new ArrayList<>();
            dbManager.dbNotes.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   listNotes.clear();
                       for (DataSnapshot u : dataSnapshot.getChildren()){
                           Notes note=u.getValue(Notes.class);
                           listNotes.add(note);
                       }


                   Collections.sort(listNotes, new StringDateComparator());
                   adapter=new NotesAdapter(choosegame.this, listNotes);
                   listView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


    private void showMessagesDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(choosegame.this);
        if (listView.getParent() != null){//for the second press on the fab.
            ((ViewGroup)listView.getParent()).removeView(listView);
        }
        builder.setView(listView);
        builder.setPositiveButton("קראתי", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fab.setImageResource(R.drawable.ic_message_gray);
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                dbManager.ifMessages=false;

                Query query= FirebaseDatabase.getInstance().getReference().child("notes")
                        .child(dbManager.userID).orderByChild("read").equalTo(false);// to bring the needed info only

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount()>0){
                            for (DataSnapshot u : dataSnapshot.getChildren()){
                                String nID=u.getKey();
                                dbManager.dbNotes.child(nID).child("read").setValue(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    class StringDateComparator implements Comparator<Notes>
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        @Override
        public int compare(Notes o1, Notes o2) {

            int res = 0;
            try {
                res= dateFormat.parse(o2.getDate()).compareTo(dateFormat.parse(o1.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return res;
        }
    }
}


