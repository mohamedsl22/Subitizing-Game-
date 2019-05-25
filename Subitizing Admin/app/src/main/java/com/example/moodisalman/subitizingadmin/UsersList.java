package com.example.moodisalman.subitizingadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersList extends AppCompatActivity {

    private ListView listView;
    private List<User> listUsers;
    private DatabaseReference dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        dbUser= FirebaseDatabase.getInstance().getReference("users");
        listView=findViewById(R.id.ListViewUsers);
        listUsers =new ArrayList<>();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user=listUsers.get(i);

                Intent intent=new Intent(getApplicationContext(),ResultsList.class);
                intent.putExtra("userid",user.getUserID());
                intent.putExtra("id",user.getId());
                intent.putExtra("name",user.getUsername());
                startActivity(intent);


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUsers.clear();
                for (DataSnapshot u : dataSnapshot.getChildren()){
                    User user=u.getValue(User.class);
                    listUsers.add(user);
                }

                UsersListAdapter adapter=new UsersListAdapter(UsersList.this, listUsers);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
