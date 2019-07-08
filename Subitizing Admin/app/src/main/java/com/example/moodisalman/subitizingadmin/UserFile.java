package com.example.moodisalman.subitizingadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import static com.example.moodisalman.subitizingadmin.AddUser.Amail;
import static com.example.moodisalman.subitizingadmin.AddUser.Apass;

/**
 * This activity for showing a specific user profile , the admin can choose to see the user
 * improvements and results by charts or by list, and also can update the user info or delete it.
 * **/


public class UserFile extends AppCompatActivity {

    private TextView txtName, txtID ,txtWinAvg, txtLoseAvg,txtTotalPlays;
    private Button btnChart, btnPieChart,btnLinChart;
    private LinearLayout btnShowRes, btnDeleteUser, btnUpdateUser, btnShowNotes;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String id , userID,name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_file);


        txtName=findViewById(R.id.txtNameRes);
        txtID=findViewById(R.id.txtIdRes);
        btnChart=findViewById(R.id.btnChart);
        btnLinChart=findViewById(R.id.btnChart2);
        btnPieChart=findViewById(R.id.btnChart3);
        btnShowRes =findViewById(R.id.showRes);
        btnDeleteUser =findViewById(R.id.deleteUser);
        btnUpdateUser =findViewById(R.id.updateUser);
        btnShowNotes=findViewById(R.id.showNotes);
        mAuth=FirebaseAuth.getInstance();
        txtLoseAvg=findViewById(R.id.txtAvgLose);
        txtWinAvg=findViewById(R.id.txtAvgWin);
        txtTotalPlays=findViewById(R.id.txtNumOfPlays);


        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        name=intent.getStringExtra("name");
        userID=intent.getStringExtra("userid");

        getInfo();

        txtName.setText(name);
        txtID.setText("ID: "+id);


        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Chart.class);
                i.putExtra("userID",userID);
                startActivity(i);
            }
        });

        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Pie_Chart.class);
                i.putExtra("userID",userID);
                startActivity(i);
            }
        });

        btnLinChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),LineChart.class);
                i.putExtra("userID",userID);
                startActivity(i);
            }
        });

        btnShowRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AllResults.class);
                i.putExtra("userID",userID);
                startActivity(i);
            }
        });

        btnShowNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),NotesList.class);
                i.putExtra("userID",userID);
                i.putExtra("name",name);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();

            }
        });



    }

    private void deleteUser(){// a function used for deleting user.
        AlertDialog.Builder deleteDialog= new AlertDialog.Builder(UserFile.this);
        deleteDialog.setTitle("Are you sure?");
        deleteDialog.setMessage("Deleting this account will result in completely removing the account" +
                " from the system and it won't be able to access the game.");

        deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                mAuth.signInWithEmailAndPassword(id+"@g.com","123456").addOnCompleteListener(UserFile.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){ // sign in with the user to be deleted (FireBase rules)
                            print("delete failed");
                        }
                        else {

                            DatabaseReference dUsers= FirebaseDatabase.getInstance().getReference("users").child(userID);
                            DatabaseReference dResults= FirebaseDatabase.getInstance().getReference("results").child(userID);
                            DatabaseReference dInfo= FirebaseDatabase.getInstance().getReference("info").child(userID);
                            DatabaseReference dNotes=FirebaseDatabase.getInstance().getReference("notes").child(userID);
                            dUsers.removeValue();
                            dResults.removeValue();
                            dInfo.removeValue();
                            dNotes.removeValue();

                            firebaseUser=mAuth.getCurrentUser();
                            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()){
                                        print("delete failed");
                                    }
                                    else{
                                        mAuth.signInWithEmailAndPassword(Amail,Apass).addOnCompleteListener(UserFile.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (!task.isSuccessful()){//sign in with the admin account again.
                                                }else {
                                                    print("Account deleted ");
                                                    finish();
                                                }

                                            }
                                        });
                                    }
                                }
                            });
                        }

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

    private void updateUser(){//to update user info

        final AlertDialog.Builder updateDialog= new AlertDialog.Builder(UserFile.this);
        updateDialog.setTitle("Update Info: "+name);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.update_dialog,null);
        updateDialog.setView(dialogView);

        final EditText edtName,edtAge;

        edtAge=dialogView.findViewById(R.id.edtAgeUpdate);
        edtName=dialogView.findViewById(R.id.edtNameUpdate);


        updateDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference db=FirebaseDatabase.getInstance().getReference("users").child(userID);
                User user=new User(edtName.getText().toString().trim(),edtAge.getText().toString(),id,userID);
                db.setValue(user);
                txtName.setText(edtName.getText().toString().trim());
                print("updated successfully");
            }
        });

        AlertDialog alertDialog=updateDialog.create();
        alertDialog.show();


    }

    public void getInfo(){//to get the info of the player and show them after the calculations.
        DatabaseReference dInfo= FirebaseDatabase.getInstance().getReference("info").child(userID);

        dInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>0){
                    ResultsInfo res=dataSnapshot.getValue(ResultsInfo.class);
                    float win=res.getWins();
                    float lose=res.getLoses();
                    int Totalplays=res.getNumOfPlays();

                    String strLose = String.format("%.2f", (lose/(lose+win))*100);
                    String strWin = String.format("%.2f", (win/(lose+win))*100);

                    txtTotalPlays.setText(String.valueOf(Totalplays));
                    txtLoseAvg.setText(strLose+"%");
                    txtWinAvg.setText(strWin+"%");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void print(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}
