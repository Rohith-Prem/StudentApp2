package com.project.iedc.studentapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class show_details extends Activity {
    EditText name,branch,sex,interest;
    SQLiteDatabase db;
    //DatabaseHandler dbhelp;
    //String tableName = DatabaseHandler.tablename;
    String studname;
    Cursor selected;
    Context contxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contxt = show_details.this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details);
        name = (EditText)findViewById(R.id.showname);
        branch = (EditText)findViewById(R.id.showbranch);
        sex = (EditText)findViewById(R.id.showgender);
        interest = (EditText)findViewById(R.id.showinterest);
        //dbhelp = new DatabaseHandler(contxt);
        //db = dbhelp.getReadableDatabase();
        Bundle bun = getIntent().getExtras();
        studname = bun.getString("stuname");

        db = getBaseContext().openOrCreateDatabase("StudentDetails",MODE_PRIVATE,null);
        selected = db.rawQuery("SELECT * FROM Details WHERE Name =?",new String [] {studname});

        if(selected!=null && selected.moveToFirst()) {
            name.setText(selected.getString(selected.getColumnIndex("Name")));
            branch.setText(selected.getString(selected.getColumnIndex("Branch")));
            sex.setText(selected.getString(selected.getColumnIndex("Gender")));
            interest.setText(selected.getString(selected.getColumnIndex("Interest")));
        }
        else{
            branch.setText("CS");
            name.setText("Roh");
        }
    }
}
