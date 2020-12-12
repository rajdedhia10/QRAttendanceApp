package com.example.attendanceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "attendance_app.db";

    public static final String TABLE1_NAME  = "login";
    public static final String COLUMN1_NAME = "id";
    public static final String COLUMN2_NAME = "name";
    public static final String COLUMN3_NAME = "roll_number";
    public static final String COLUMN4_NAME = "password";

    public static final String TABLE2_NAME  = "attendance";
    public static final String COLUMN21_NAME = "id";
    public static final String COLUMN22_NAME = "roll_number";
    public static final String COLUMN23_NAME = "class";
    public static final String COLUMN24_NAME = "time";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE2_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ROLL_NUMBER TEXT, CLASS TEXT, TIME TEXT )");
        db.execSQL("CREATE TABLE " + TABLE1_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, ROLL_NUMBER TEXT, PASSWORD TEXT ) \n ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        onCreate(db);
    }

    public boolean insertDataInLogin(String name, String rollNumber, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues  contentValues = new ContentValues();
        contentValues.put(COLUMN2_NAME, name);
        contentValues.put(COLUMN3_NAME, rollNumber);
        contentValues.put(COLUMN4_NAME, password);
        long result = db.insert(TABLE1_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertDataInAttendance(String rollNumber, String classText, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues  contentValues = new ContentValues();
        contentValues.put(COLUMN22_NAME, rollNumber);
        contentValues.put(COLUMN23_NAME, classText);
        contentValues.put(COLUMN24_NAME, time);
        long result = db.insert(TABLE2_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor authenticateUser(String rollNumber, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE ROLL_NUMBER = '" + rollNumber + "' AND PASSWORD = '" + password + "'";
        Log.i("Debugging", query);
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public Cursor getAllDataFromAttendance() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE2_NAME;
        Log.i("Debugging", query);
        Cursor res = db.rawQuery(query, null);
        return res;
    }

}
