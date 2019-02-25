package com.example.moodisalman.subitizing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Answers extends AppCompatDialogFragment {
    private Button btn1,btn2,btn3,btn4;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.activity_answers,null);
        builder.setView(view).setTitle("תשובות");



        return builder.create();
    }
//        private Button btn1,btn2,btn3,btn4;
//    protected String visibleObj ;
//    private Intent intent;
//    private int NumOfFails=0  , intSuccess=0 ,time;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_answers);
//
//        btn1=findViewById(R.id.btn1);
//        btn2=findViewById(R.id.btn2);
//        btn3=findViewById(R.id.btn3);
//        btn4=findViewById(R.id.btn4);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            visibleObj = extras.getString("key");
//            intSuccess = extras.getInt("success");
//            time=extras.getInt("time");
//            //The key argument here must match that used in the other activity
//        }
//
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (btn1.getText().toString().equalsIgnoreCase(visibleObj)){
//                    intent = new Intent(getBaseContext(),MainActivity.class);
//                    if (NumOfFails==0){
//                        intSuccess++;
//                        intent.putExtra("success" , intSuccess);
//                        intent.putExtra("time",time);
//                    }
//                    startActivity(intent);}
//                else
//                    NumOfFails++;
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (btn2.getText().toString().equalsIgnoreCase(visibleObj)){
//                    intent = new Intent(getBaseContext(),MainActivity.class);
//                    if (NumOfFails==0){
//                        intSuccess++;
//                        intent.putExtra("success" , intSuccess);
//                        intent.putExtra("time",time);
//                    }
//                    startActivity(intent);}
//                else
//                    NumOfFails++;
//            }
//        });
//
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (btn3.getText().toString().equalsIgnoreCase(visibleObj)){
//                    intent = new Intent(getBaseContext(),MainActivity.class);
//                    if (NumOfFails==0){
//                        intSuccess++;
//                        intent.putExtra("success" , intSuccess);
//                        intent.putExtra("time",time);
//                    }
//                    startActivity(intent);}
//                else
//                    NumOfFails++;
//            }
//        });
//
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (btn4.getText().toString().equalsIgnoreCase(visibleObj)){
//                    intent = new Intent(getBaseContext(),MainActivity.class);
//                    if (NumOfFails==0){
//                        intSuccess++;
//                        intent.putExtra("success" , intSuccess);
//                        intent.putExtra("time",time);
//                    }
//                    startActivity(intent);}
//                else
//                    NumOfFails++;
//            }
//        });
//    }
}
