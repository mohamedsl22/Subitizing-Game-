package com.example.moodisalman.subitizingadmin;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * This activity is used to show all results for a specific user in line chart**/

public class LineChart extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private LineChartView lineChartView;
    private Query query;
    private String userID;
    private List axisValues,yAxisValues;
    private Line line;
    private Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        lineChartView = findViewById(R.id.LineChart);

        Intent intent=getIntent();
        userID=intent.getStringExtra("userID");


        spn=findViewById(R.id.spinnerLin);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.levels,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        spn.setOnItemSelectedListener(this);




    }

    private void drawChart(String lev) {

        yAxisValues = new ArrayList();
        axisValues = new ArrayList();

        line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

        query= FirebaseDatabase.getInstance().getReference().child("results").child(userID).orderByChild("level")
                .equalTo(lev);// to bring the needed info only


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount()>0){//means there is results (not empty)
                    int i=0;
                    for (DataSnapshot u : dataSnapshot.getChildren()){
                        Result res=u.getValue(Result.class);
                        float win=Float.parseFloat(res.getWin());
                        float lose=Float.parseFloat(res.getLose());
                        float perc=100-((lose/(win+lose))*100);

                        yAxisValues.add(new PointValue(i, perc));
                        axisValues.add(i, new AxisValue(i).setLabel(String.valueOf(i)));
                        i++;
                    }

                    List lines = new ArrayList();
                    lines.add(line);

                    LineChartData data = new LineChartData();
                    data.setLines(lines);

                    lineChartView.setLineChartData(data);

                    Axis axis = new Axis();
                    axis.setValues(axisValues);
                    axis.setTextSize(16);
                    axis.setTextColor(Color.parseColor("#03A9F4"));
                    data.setAxisXBottom(axis);

                    Axis yAxis = new Axis();
                    yAxis.setTextColor(Color.parseColor("#03A9F4"));
                    yAxis.setTextSize(16);
                    data.setAxisYLeft(yAxis);

                    yAxis.setName("Improvement in percent %");

                    lineChartView.setLineChartData(data);
                    Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
                    viewport.top = 110;
                    lineChartView.setMaximumViewport(viewport);
                    lineChartView.setCurrentViewport(viewport);

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
}
