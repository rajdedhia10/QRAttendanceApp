package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class Attendance extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView attendanceTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        myDb = new DatabaseHelper(this);
        Cursor res = myDb.getAllDataFromAttendance();
        if(res.getCount() > 0) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Class          " + "Time" + "\n");
            while (res.moveToNext()) {
//                buffer.append("Roll Number: " + res.getString(1) + "\n");
//                buffer.append("Class: " + res.getString(2) + "\n");
//                buffer.append("Time: " + res.getString(3) + "\n\n");
                buffer.append(res.getString(2) + "        ");
                buffer.append(res.getString(3) + "\n");
            }
            attendanceTextView = (TextView) findViewById(R.id.attendanceTextView);
            attendanceTextView.setText(buffer.toString());
        }
    }
}