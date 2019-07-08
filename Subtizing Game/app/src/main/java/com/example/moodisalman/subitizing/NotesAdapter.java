package com.example.moodisalman.subitizing;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * This class used as an adapter for the Noteslist **/

public class NotesAdapter extends ArrayAdapter<Notes> {
    private Activity context;
    private List<Notes> notesList;

    public NotesAdapter(Activity context, List<Notes> notesList) {
        super(context,R.layout.one_note,notesList);
        this.context = context;
        this.notesList = notesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem=inflater.inflate(R.layout.one_note,null,true);

        TextView txtName=listViewItem.findViewById(R.id.aNametxt);
        TextView txtNote=listViewItem.findViewById(R.id.aVersiontxt);
        TextView txtDate=listViewItem.findViewById(R.id.textView3);
        ImageView imgRead=listViewItem.findViewById(R.id.appIconIV);

        Notes note=notesList.get(position);

        txtName.setText("ֿֿֿֿֿ“"+note.getNote()+"”");
        txtNote.setText("שם: "+note.getUserName());
        txtDate.setText(note.getDate());
        boolean isRead=note.isRead();
        if (!isRead)
            imgRead.setImageResource(R.drawable.red_light);
        else
            imgRead.setImageResource(R.drawable.green_light);


        return listViewItem;
    }
}
