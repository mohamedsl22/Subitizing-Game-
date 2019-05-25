package com.example.moodisalman.subitizingadmin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class UsersListAdapter extends ArrayAdapter<User> {

    private Activity context;
    private List<User> userList;

    public UsersListAdapter(Activity context, List<User> userList){
        super(context,R.layout.list_layout,userList);
        this.context=context;
        this.userList=userList;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);

        TextView txtName=listViewItem.findViewById(R.id.textViewName);
        TextView txtId=listViewItem.findViewById(R.id.textViewID);
        TextView txtAge=listViewItem.findViewById(R.id.textViewAge);

        User user=userList.get(position);

        txtName.setText(user.getUsername());
        txtId.setText("ID: "+user.getId());
        txtAge.setText("Age: "+user.getAge());

        return listViewItem;
    }
}
