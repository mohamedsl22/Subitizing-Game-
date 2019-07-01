package com.example.moodisalman.subitizingadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This activity is used to show all results for a specific user in bar chart**/

public class Chart extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String userID;
    private Query query;
    private List<BarEntry> yVals1;
    private List<BarEntry> yVals2;
    private BarChart barChart;
    private Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent=getIntent();
        userID=intent.getStringExtra("userID");


        spn=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.levels,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        spn.setOnItemSelectedListener(this);


    }


    private void drawChart(String lev) {
        barChart = findViewById(R.id.barChart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false);

        yVals1 = new ArrayList<>();
        yVals2 = new ArrayList<>();

        XAxis xl = barChart.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        barChart.getAxisRight().setEnabled(false);

        //data
        final float groupSpace = 0.09f;
        final float barSpace = 0.02f;
        final float barWidth = 0.40f;




        query= FirebaseDatabase.getInstance().getReference().child("results").child(userID).orderByChild("level")
                .equalTo(lev);

        query.addListenerForSingleValueEvent(new ValueEventListener() {// to bring all the needed data
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>0){//means there is results (not empty)
                    int i=1;
                    for (DataSnapshot u : dataSnapshot.getChildren()){
                        Result res=u.getValue(Result.class);
                        yVals1.add(new BarEntry(i,Float.parseFloat(res.getWin())));
                        yVals2.add(new BarEntry(i, Float.parseFloat(res.getLose())));
                        Log.e("debugkk","win: "+res.getWin()
                        +" lose: "+res.getLose()+" i= "+i);
                        i++;
                    }

                    BarDataSet set1, set2;

                    if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
                        set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
                        set2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
                        set1.setValues(yVals1);
                        set2.setValues(yVals2);
                        barChart.getData().notifyDataChanged();
                        barChart.notifyDataSetChanged();
                    } else {
                        set1 = new BarDataSet(yVals1, "Wins");
                        set1.setColor(Color.rgb(21,132,4));
                        set2 = new BarDataSet(yVals2, "Losses");
                        set2.setColor(Color.rgb(196,0,0));

                        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1);
                        dataSets.add(set2);
                        BarData data = new BarData(dataSets);
                        barChart.setData(data);
                    }

                    barChart.getBarData().setBarWidth(barWidth);
                    barChart.groupBars(0.5f, groupSpace, barSpace);
                    barChart.animateY(1000);
                    barChart.invalidate();

                }else{
                    yVals1.clear();
                    yVals2.clear();
                    BarDataSet set1, set2;
                    set1 = new BarDataSet(yVals1, "Wins");
                    set1.setColor(Color.rgb(21,132,4));
                    set2 = new BarDataSet(yVals2, "Losses");
                    set2.setColor(Color.rgb(196,0,0));

                    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);
                    dataSets.add(set2);
                    BarData data = new BarData(dataSets);
                    barChart.setData(data);

                barChart.getBarData().setBarWidth(barWidth);
                barChart.groupBars(0.5f, groupSpace, barSpace);
                barChart.animateY(1000);
                barChart.invalidate();
                print("No data to show.");

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String lev=adapterView.getItemAtPosition(i).toString();
        drawChart(lev);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void print(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
