package com.example.software2.bloodbankmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

     private static final String DB_NAME = "testdata";
      // below int is our database version
    private static final int DB_VERSION = 1;
        public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

     // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        String query =  " CREATE TABLE register " +
                "( Name  TEXT, age TEXT, gender TEXT, " +
                "Email TEXT, Username TEXT, TYPE UNIQUE," +
                " password TEXT, contactNo TEXT, Address TEXT, " +
                "city TEXT, img Blob, bloodgrp TEXT, Uid TEXT," +
                " CHECK ( bloodgrp in ('A+','A-','B+','B-','AB+','AB-','O-','O+')));";
        db.execSQL(query);

        String query2 =  " create table donor " +
                "( Name TEXT, Email TEXT, contactNo TEXT," +
                "Address TEXT, city TEXT, donor Text, bloodgrp TEXT," +
                " img BLOB, Username TEXT, Uid TEXT, " +
                "check( bloodgrp in('A+','A-','B+','B-','AB+','AB-','O-','O+')))";
        db.execSQL(query2);

        String query3 =  "create table request(" +
                "bloodgrp TEXT," +
                "Name TEXT ," +
                "contactNo TEXT ," +
                "Username TEXT," +
                 "time TEXT," +
                "Datetime1 TEXT,"+
                "message TEXT"+")";
        db.execSQL(query3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
