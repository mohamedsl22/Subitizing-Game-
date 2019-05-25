package com.example.moodisalman.subitizingadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResultsList extends AppCompatActivity {

    private ListView listView;
    private List<Result> listResults;
    private DatabaseReference dbUser;
    private TextView txtName, txtID;
    private DatabaseReference getDbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_list);

        dbUser= FirebaseDatabase.getInstance().getReference("results");
        listView=findViewById(R.id.ListViewResults);
        listResults =new ArrayList<>();
        txtName=findViewById(R.id.txtNameRes);
        txtID=findViewById(R.id.txtIdRes);

        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        String name=intent.getStringExtra("name");
        String userID=intent.getStringExtra("userid");

        txtName.setText(name);
        txtID.setText("ID: "+id);

        getDbUser=FirebaseDatabase.getInstance().getReference("results").child(userID);



    }

    @Override
    protected void onStart() {
        super.onStart();

        getDbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listResults.clear();
                for (DataSnapshot u : dataSnapshot.getChildren()){
                    Result res=u.getValue(Result.class);
                    listResults.add(res);
                }

                ResultsListAdapter adapter=new ResultsListAdapter(ResultsList.this, listResults);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
