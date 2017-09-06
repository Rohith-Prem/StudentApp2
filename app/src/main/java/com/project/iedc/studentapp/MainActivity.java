package com.project.iedc.studentapp;



import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;
import java.util.ArrayList;

import java.util.logging.LogRecord;

import static com.project.iedc.studentapp.R.string.submit;

    public class MainActivity extends AppCompatActivity {

        ArrayList<String> results = new ArrayList<String>();
        String tableName = DatabaseHandler.tablename;
        SQLiteDatabase newDB;
        Button addstu;
        ListView listview;
        String nametxt, branchtxt;
        boolean backPressed;
         /**
         * Called when the activity is first created.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            listview = (ListView) findViewById(R.id.studlist);
            newDB = getBaseContext().openOrCreateDatabase("StudentDetails",MODE_PRIVATE,null);
            newDB.execSQL("CREATE TABLE IF NOT EXISTS Details (Name TEXT NOT NULL PRIMARY KEY, Branch TEXT, Gender TEXT, Interest TEXT);");
            openAndQueryDatabase();//read records into list adapter array
            displayResultList();//apply array to adapter

            //add student button
            addstu = (Button) findViewById(R.id.btn);
            addstu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addstud = new Intent(MainActivity.this, add_student.class);
                    startActivity(addstud);
                    finish();
                }
            });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listadapter, View view, int pos, long id){
                String selectedFromList =(String) (results.get(pos));
                int i =selectedFromList.indexOf(':');
                int j=selectedFromList.indexOf(" BRANCH:");
                selectedFromList=selectedFromList.substring(i+1,j);
                Toast.makeText(MainActivity.this,""+selectedFromList+"'s Details",Toast.LENGTH_SHORT).show();
                Intent showdetails = new Intent(MainActivity.this,show_details.class);
                Bundle bun = new Bundle();
                bun.putString("stuname",selectedFromList);
                showdetails.putExtras(bun);
                startActivity(showdetails);
            }
        });

        }

        //method to apply list array to adapter
        private void displayResultList() {
            ArrayAdapter listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);
            listview.setAdapter(listadapter);
            listview.setTextFilterEnabled(true);
        }

        //method to store records from DB into array
        private void openAndQueryDatabase() {
            try {
                //DatabaseHandler dbHelper = new DatabaseHandler(this.getApplicationContext());
                //newDB = dbHelper.getReadableDatabase();
                Cursor c = newDB.rawQuery("SELECT Name, Branch FROM " + tableName, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            nametxt = c.getString(0);
                            branchtxt = c.getString(1);
                            results.add("NAME:"+nametxt+" BRANCH:"+branchtxt);

                        } while (c.moveToNext());
                    }
                }
            } catch (SQLiteException se) {
                Log.e(getClass().getSimpleName(), "Could not create or Open the database");
            } finally {
                if (newDB != null)
                    //newDB.execSQL("DELETE FROM " + tableName);
                    newDB.close();
            }
            displayResultList();
        }

        //to implement exit on double backpress
        @Override
        public void onBackPressed() {
            if (backPressed) {
                super.onBackPressed();
                }else{
                Snackbar.make(addstu, "Tap again to exit",Snackbar.LENGTH_SHORT).show();
                backPressed = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPressed = false;
                    }
                }, 1000);//waits for 1 second and then executes code inside run()
            }
        }

        //creating menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu1, menu1);
            return true;
        }

        //menu item selected handler
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.credits:
                    Toast.makeText(MainActivity.this, "Rohith P", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.web:
                    Intent website = new Intent(MainActivity.this, webview.class);
                    Bundle bundle2 = new Bundle();
                    String url = "http://www.google.com";
                    bundle2.putString("url",url);
                    website.putExtras(bundle2);
                    startActivity(website);
                default:
                    return super.onOptionsItemSelected(item);
            }
        }


    }
