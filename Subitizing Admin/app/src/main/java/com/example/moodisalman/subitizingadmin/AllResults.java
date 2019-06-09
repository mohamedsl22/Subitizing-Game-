package com.example.moodisalman.subitizingadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllResults extends AppCompatActivity {

    private ListView listView;
    private List<Result> listResults;
    private DatabaseReference dbUser;
    private DatabaseReference getDbUser;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_results);

        listView=findViewById(R.id.ListViewResults);
        progressBar=findViewById(R.id.progressBar3);

        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        String name=intent.getStringExtra("name");
        final String userID=intent.getStringExtra("userID");


        dbUser= FirebaseDatabase.getInstance().getReference("results");
        getDbUser=FirebaseDatabase.getInstance().getReference("results").child(userID);

        listResults =new ArrayList<>();


        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getDbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                listResults.clear();
                for (DataSnapshot u : dataSnapshot.getChildren()){
                    Result res=u.getValue(Result.class);
                    listResults.add(res);
                }

                ResultsListAdapter adapter=new ResultsListAdapter(AllResults.this, listResults);
                listView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
