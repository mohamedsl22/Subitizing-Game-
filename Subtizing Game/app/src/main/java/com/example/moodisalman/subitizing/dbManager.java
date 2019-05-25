package com.example.moodisalman.subitizing;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class dbManager {

    static DatabaseReference dbUser, dbResult;
    static Vector<String> userVec;
    static String userID;
    static FirebaseAuth mAuth;

    public dbManager(){
        dbUser= FirebaseDatabase.getInstance().getReference("users");
        mAuth=FirebaseAuth.getInstance();
    }



    static void initDbResult(){// init for the dbResult when the userID isn't null
        dbResult=FirebaseDatabase.getInstance().getReference("results").child(userID);
    }

}
