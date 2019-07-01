package com.example.moodisalman.subitizing;



import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**This class contains all the DB variables that is used in more than one class , it also init the wanted DB when needed**/

public class dbManager {

    static DatabaseReference dbUser, dbResult , dbResultsInfo;
    static String userID;
    static FirebaseAuth mAuth;
    static int TotalGames=0, TotalWins=0,TotalLoses=0;

    public dbManager(){
        dbUser= FirebaseDatabase.getInstance().getReference("users");
        mAuth=FirebaseAuth.getInstance();
    }



    static void initDbResult(){// init for the dbResult & dbResultsInfo when the userID isn't null
        dbResultsInfo=FirebaseDatabase.getInstance().getReference("info").child(userID);
        dbResult=FirebaseDatabase.getInstance().getReference("results").child(userID);


        dbResultsInfo.addValueEventListener(new ValueEventListener() {//to get wins and losses to add to them
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>0){//means there is results (not empty)
                        ResultsInfo res=dataSnapshot.getValue(ResultsInfo.class);
                        TotalGames =res.getNumOfPlays();
                        TotalLoses=res.getLoses();
                        TotalWins=res.getWins();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
