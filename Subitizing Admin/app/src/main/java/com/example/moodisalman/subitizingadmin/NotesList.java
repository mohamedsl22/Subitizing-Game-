package com.example.moodisalman.subitizingadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

/**
 * This activity used to show all the notes of the user , or to show a specific users notes**/

public class NotesList extends AppCompatActivity {

    private ListView listView;
    private List<Notes> listNotes;
    private DatabaseReference dbNote, dbNoteChild;
    private ProgressBar progressBar;
    private NotesAdapter adapter;
    private FloatingActionButton fab;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        dbNote= FirebaseDatabase.getInstance().getReference("notes");
        listView=findViewById(R.id.notesList);
        listNotes=new ArrayList<>();
        progressBar=findViewById(R.id.progressBar5);

    }

    @Override
    protected void onStart() {
        super.onStart();


        Intent intent=getIntent();
        final String id=intent.getStringExtra("id");
        final String name=intent.getStringExtra("name");
        userID=intent.getStringExtra("userID");

        if (userID==null) {// means that the user got into the page from the MainActivity

            dbNote.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                    listNotes.clear();
                    for (DataSnapshot u : dataSnapshot.getChildren()) {
                        String key = u.getKey();
                        dbNoteChild = dbNote.child(key);
                        dbNoteChild.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot u : dataSnapshot.getChildren()) {
                                    Notes note = u.getValue(Notes.class);
                                    listNotes.add(note);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    Collections.sort(listNotes, new StringDateComparator());
                    adapter = new NotesAdapter(NotesList.this, listNotes);
//                listView.setTextFilterEnabled(true);
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{//means that the user got into the page from userFile

            fab=findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);// to show the fab button, to add notes

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getApplicationContext(),AddNote.class);
                    i.putExtra("userID",userID);
                    i.putExtra("name",name);
                    i.putExtra("id",id);
                    startActivity(i);
                }
            });


            dbNote.child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                    listNotes.clear();
                    for (DataSnapshot u : dataSnapshot.getChildren()) {
                        Notes note = u.getValue(Notes.class);
                        listNotes.add(note);
                    }
                    Collections.sort(listNotes, new StringDateComparator());
                    adapter = new NotesAdapter(NotesList.this, listNotes);
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                if (userID!=null){
                    Notes n=listNotes.get(pos);
                    String nDate=n.getDate();
                    deleteNote(nDate);
                }
                else
                    print("To delete go the user file.");
                return true;
            }
        });

    }

    private void print(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }





    private void deleteNote(final String nDate){
        AlertDialog.Builder deleteDialog= new AlertDialog.Builder(NotesList.this);
        deleteDialog.setTitle("Are you sure?");


        deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query query= FirebaseDatabase.getInstance().getReference().child("notes").child(userID).orderByChild("date")
                        .equalTo(nDate);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount()>0){
                            for (DataSnapshot u : dataSnapshot.getChildren()){
                                String nID =u.getKey();
                                FirebaseDatabase.getInstance().getReference("notes").child(userID)
                                        .child(nID).removeValue();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        deleteDialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog=deleteDialog.create();
        alertDialog.show();

    }








    class StringDateComparator implements Comparator<Notes>//the class used to sort notes according to dates
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
