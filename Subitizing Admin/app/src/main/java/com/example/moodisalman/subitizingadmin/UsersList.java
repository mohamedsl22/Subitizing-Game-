package com.example.moodisalman.subitizingadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity for showing the users list, when the admin get into this activity, all the users
 * info will be retrieved from the fireBase and showed in a list.
 **/

public class UsersList extends AppCompatActivity {

    private ListView listView;
    private List<User> listUsers;
    private DatabaseReference dbUser;
    private ProgressBar progressBar;
    private EditText edtSearch;
    private UsersListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        dbUser= FirebaseDatabase.getInstance().getReference("users");
        listView=findViewById(R.id.ListViewUsers);
        progressBar=findViewById(R.id.progressBar2);
        listUsers =new ArrayList<>();
        edtSearch=findViewById(R.id.edtSearch);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user=listUsers.get(i);

                Intent intent=new Intent(getApplicationContext(), UserFile.class);
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
                progressBar.setVisibility(View.VISIBLE);
                listUsers.clear();
                for (DataSnapshot u : dataSnapshot.getChildren()){
                    User user=u.getValue(User.class);
                    listUsers.add(user);
                }

                adapter=new UsersListAdapter(UsersList.this, listUsers);
                listView.setTextFilterEnabled(true);
                listView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
                int textlength = cs.length();
                List<User> tmpArrayUsersList = new ArrayList<>();

                if (cs.toString().equalsIgnoreCase("")){
                    tmpArrayUsersList.clear();
                    adapter=new UsersListAdapter(UsersList.this, listUsers);
                    listView.setAdapter(adapter);

                }

                else {

                for(User c: listUsers){
                    if (textlength <= c.getUsername().length()) {
                        if (c.getUsername().toLowerCase().contains(cs.toString().toLowerCase())) {
                            tmpArrayUsersList.add(c);
                        }
                    }
                    if (c.getId().contains(cs.toString()))
                        tmpArrayUsersList.add(c);
                }
                UsersListAdapter mAdapter = new UsersListAdapter(UsersList.this, tmpArrayUsersList);
                listView.setAdapter(mAdapter);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
