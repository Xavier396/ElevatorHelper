package com.example.elevatorhelper.Impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author yanghaijia
 */
public class DBMaker extends SQLiteOpenHelper {


    public DBMaker(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1="create table Elevator (id INTEGER PRIMARY KEY AUTOINCREMENT,elevator_number VARCHAR(255),elevator_status INTEGER,elevator_comment VARCHAR(255))";

        String sql2="create table User (id INTEGER PRIMARY KEY AUTOINCREMENT,user_name VARCHAR(255),user_phone VARCHAR(20),user_password_hash VARCHAR(50),user_head VARCHAR(10))";

        String sql3="create table Issue (id INTEGER PRIMARY KEY AUTOINCREMENT,elevator_location VARCHAR(255),elevator_issue VARCHAR(255),elevator_time VARCHAR(50) )";

        db.execSQL("DROP TABLE IF EXISTS Elevator");
        Log.i("DatabaseOperator","Elevator Table Cleaned!");
        db.execSQL(sql1);
        Log.i("DatabaseOperator","Elevator Table Created!");

        db.execSQL("DROP TABLE IF EXISTS User");
        Log.i("DatabaseOperator","User Table Cleaned!");
        db.execSQL(sql2);
        Log.i("DatabaseOperator","User Table Created!");

        db.execSQL("DROP TABLE IF EXISTS Issue");
        Log.i("DatabaseOperator","User Table Cleaned!");
        db.execSQL(sql3);
        Log.i("DatabaseOperator","User Table Created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DatabaseOperator","DB update");
    }
}
