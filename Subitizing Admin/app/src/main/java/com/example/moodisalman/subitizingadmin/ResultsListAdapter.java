package com.example.moodisalman.subitizingadmin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * This class used as an adapter for the results list **/

public class ResultsListAdapter extends ArrayAdapter<Result> {
    private Activity context;
    private List<Result> resList;

    public ResultsListAdapter(Activity context, List<Result> resList){
        super(context,R.layout.result_list, resList);
        this.context=context;
        this.resList = resList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.result_list,null,true);

        TextView txtdate=listViewItem.findViewById(R.id.txtdate);
        TextView txtLev=listViewItem.findViewById(R.id.txtLev);
        TextView txtWin=listViewItem.findViewById(R.id.txtwins);
        TextView txtLose=listViewItem.findViewById(R.id.txtlose);
        TextView txtToltal=listViewItem.findViewById(R.id.txtTotal);
        TextView txtTimeinMilSec=listViewItem.findViewById(R.id.txttime);

        Result res= resList.get(position);

        txtdate.setText("Date: "+res.getDate());
        txtLev.setText("Level: "+res.getLevel());
        txtWin.setText("Wins: "+res.getWin());
        txtLose.setText("Losses: "+res.getLose());

        int wins= Integer.parseInt(res.getWin());
        int losses= Integer.parseInt(res.getLose());

        txtToltal.setText("Total: "+(wins+losses));
        txtTimeinMilSec.setText("Final time in MillSec: "+res.timeInMillSec);

        return listViewItem;
    }
}
