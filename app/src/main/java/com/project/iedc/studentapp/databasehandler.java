package com.project.iedc.studentapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Rohith on 27-08-2017.
 */

class DatabaseHandler extends SQLiteOpenHelper{

    private SQLiteDatabase db;
    //Database Version
    private static final int dbversion  = 1;
    //Database name 
    private static final String dbname = "StudentDetails";
    //Table name
    static final String tablename = "Details";
    private static String path;
    private Context currentContext;

    //class constructor
    DatabaseHandler(Context context) {
        super(context, dbname, null, dbversion);
        currentContext = context;
        path = "/data/data/" +context.getPackageName()+ "/databases/";
        createDatabase();
    }



    //method to create database
    private void createDatabase() {
        boolean isdbExists = checkDbExists();

        if (isdbExists) {
            // do nothing
        }
        else {
            db = currentContext.openOrCreateDatabase(dbname, 0, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "+tablename+" Name TEXT NOT NULL PRIMARY KEY, Branch TEXT, Gender TEXT, Interest TEXT ");
            //db.execSQL("INSERT INTO " +tablename +" VALUES ('Rohith','CS','male','App');");

        }
    }

    //method to check if databse already exists
    private boolean checkDbExists() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = path + dbname;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    //method to insert values
    public void insertValues(String name,String branch,String sex,String interest){
        db.execSQL("INSERT INTO " + tablename + " VALUES('"+name+"','"+branch+"','"+sex+"','"+interest+"')");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}

