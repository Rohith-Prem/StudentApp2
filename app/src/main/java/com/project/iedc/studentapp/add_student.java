package com.project.iedc.studentapp;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class add_student extends Activity {
        Spinner branch;
        EditText name;
        Button submit;
        RadioButton male, female,g;
        RadioGroup gender;
        String nametxt,branchtxt,interesttxt ="",gendertxt;
        CheckBox cbweb, cbapp, cbps, cbanim;
        SQLiteDatabase studdetails;
        DatabaseHandler dbhand;
        String table = DatabaseHandler.tablename;
        int rbid;
        int count;
        SharedPreferences preferences;
        AlertDialog.Builder builder;
        Context c;




        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_student);
            branch = (Spinner) findViewById(R.id.spinner);
            name = (EditText) findViewById(R.id.form_name);
            submit = (Button) findViewById(R.id.subbtn);
            male = (RadioButton) findViewById(R.id.rbmale);
            female = (RadioButton) findViewById(R.id.rbfemale);
            cbweb = (CheckBox) findViewById(R.id.webdes);
            cbapp = (CheckBox) findViewById(R.id.appdev);
            cbps = (CheckBox) findViewById(R.id.photo);
            cbanim = (CheckBox) findViewById(R.id.anim);
            gender = (RadioGroup)findViewById(R.id.gendergrp);

            final Intent goback = new Intent(add_student.this,MainActivity.class);
            c = add_student.this.getApplicationContext();

            dbhand = new DatabaseHandler(c);
            studdetails = dbhand.getWritableDatabase();

            preferences = getSharedPreferences("Counter",MODE_PRIVATE);

            builder = new AlertDialog.Builder(add_student.this);
            builder.setTitle("Name Already Exists/Invalid Name");
            builder.setMessage("Do you want to enter again?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(add_student.this,"Enter Again", Toast.LENGTH_SHORT).show();
                    name.setText("");

                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    branchtxt = parent.getItemAtPosition(pos).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    branchtxt = "Not Entered";

                }});

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    nametxt = name.getText().toString();
                    //checking whether record with same name exists
                    Cursor result = studdetails.rawQuery("SELECT Name FROM " + table + " WHERE name =?", new String [] {nametxt});
                    result.moveToFirst();
                    if (!result.isAfterLast() || nametxt.isEmpty()) {
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                        result.close();
                        //finding gender
                        rbid = gender.getCheckedRadioButtonId();
                        g = (RadioButton) findViewById(rbid);
                        gendertxt = g.getText().toString();
                        //collecting interests
                        if (cbweb.isChecked())
                            interesttxt = interesttxt + " Web Designing,";

                        if (cbapp.isChecked())
                            interesttxt = interesttxt + " App Development,";

                        if (cbps.isChecked())
                            interesttxt = interesttxt + " Photoshop,";

                        if (cbanim.isChecked())
                            interesttxt = interesttxt + " ,Animation,";

                        studdetails.execSQL("INSERT INTO " + table + " VALUES('" + nametxt + "','" + branchtxt + "','" + gendertxt + "','" + interesttxt + "')");

                        count = preferences.getInt("register_count", 0);
                        count = count + 1;
                        preferences.edit().putInt("register_count", count).apply();
                        Snackbar.make(submit, " " + preferences.getInt("register_count", count) + " Entries", Snackbar.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(goback);
                                finish();
                            }
                        }, 2000);


                    }
            });


        }

}
