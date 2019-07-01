package com.example.moodisalman.subitizingadmin;

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
import android.widget.Toast;


/**
 * the class connected with the layout_login_dialog.xml for the login dialog, for the admin login.
 *
 * **/
public class loginDialog extends AppCompatDialogFragment {

    private EditText edtMail, edtPass;
    private Intent i;
    private LoginDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.login_dailog,null);

        edtMail= view.findViewById(R.id.edtMail);
        edtPass=view.findViewById(R.id.edtpass);

        builder.setView(view).setTitle("Login")
                .setPositiveButton("login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                if (edtMail.getText().toString().trim().length() > 0
//                        || edtPass.getText().toString().trim().length() > 0){
//                    String mailText="";
//                    String passText="";
//                    listener.applyText(mailText,passText);
//                }
//                else{

                    String mailText=edtMail.getText().toString().trim();
                    String passText=edtPass.getText().toString().trim();
                    listener.applyText(mailText,passText);
//                }

            }
        });

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {// when there is login info
        super.onAttach(context);

        try {
            listener=(LoginDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement LoginDialogListener");
        }
    }

    public interface LoginDialogListener{
        void applyText(String mail , String pass);
    }

    private void print(String s ){
        Toast.makeText(getContext(),s, Toast.LENGTH_SHORT).show();
    }
}
