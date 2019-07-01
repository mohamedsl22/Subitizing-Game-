package com.example.moodisalman.subitizingadmin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This activity is used to show all results for a specific user in pie chart**/

public class Pie_Chart extends AppCompatActivity {
    private PieChart pieChart;
    private String userID ,lev;
    private Query query;
    private ArrayList<PieEntry> yvalues;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        progressBar=findViewById(R.id.progressBarPieChart);

        Intent intent=getIntent();
        userID=intent.getStringExtra("userID");

        yvalues = new ArrayList<>();

        retrieveData(1);


    }

    private void drawChart() {

        final int[] COLORFUL_COLORS = {
                Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
                Color.rgb(106, 150, 31), Color.rgb(179, 100, 53), Color.rgb(255, 208, 140)
        };
        pieChart = findViewById(R.id.pieChart);
//        pieChart.setUsePercentValues(true);
        progressBar.setVisibility(View.GONE);


        PieDataSet dataSet = new PieDataSet(yvalues,"");
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setUsePercentValues(true);
        pieChart.setData(data);
        Description description = new Description();
        description.setText("improvement in percent%");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(55f);
        dataSet.setColors(COLORFUL_COLORS);
        pieChart.animateY(1000);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.invalidate();
    }

    private void retrieveData(final int o){

        if (o==7){
            drawChart();
            return;
        }

        lev=String.valueOf(o);
        query= FirebaseDatabase.getInstance().getReference().child("results").child(userID).orderByChild("level")
                .equalTo(lev);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>0){
                    progressBar.setVisibility(View.VISIBLE);
                    float win=0,lose=0;
                    for (DataSnapshot u : dataSnapshot.getChildren()){
                        Result res=u.getValue(Result.class);
                        win+=Float.parseFloat(res.getWin());
                        lose+=Float.parseFloat(res.getLose());

                    }

                    float percFinal=((win/(win+lose))*100);
                    yvalues.add(new PieEntry(percFinal, "Level "+o, o-1));
                    retrieveData(o+1);

                }
                else{
                    retrieveData(o+1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

}
