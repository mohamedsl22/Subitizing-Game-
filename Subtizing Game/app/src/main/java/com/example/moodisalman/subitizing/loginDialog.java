package com.example.moodisalman.subitizing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


/**
 * the class connected with the layout_login_dialog.xml for the login dialog, to either decide if to login
 * or a guest.
 *
 * **/
public class loginDialog extends AppCompatDialogFragment {

    private EditText edtId;
    private Intent i;
    private LoginDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_login_dialog,null);

        edtId= view.findViewById(R.id.editId);

        builder.setView(view).setTitle("Login")
                .setNeutralButton("guest", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i = new Intent(getContext(), choosegame.class);
                startActivity(i);
            }
        }).setPositiveButton("login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idText=edtId.getText().toString();

                listener.applyText(idText);

            }
        });


        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener=(LoginDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement LoginDialogListener");
        }
    }

    public interface LoginDialogListener{
        void applyText(String id);
    }
}
