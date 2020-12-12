package com.example.attendanceapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckInCheckOut extends AppCompatActivity {
    DatabaseHelper myDb;
    private SharedPreferences sharedpreferences;
    TextView usernameTextView;
    String rollNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_check_out);
        usernameTextView= findViewById(R.id.userNameTextView);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        rollNumber = sharedpreferences.getString("rollNumber", "");
        usernameTextView.setText(rollNumber);
        myDb = new DatabaseHelper(this);
    }

    public void checkInCheckOut(View View) {
        startActivity(new Intent(CheckInCheckOut.this, Scan.class));
    }

    public void attendance(View View) {

        startActivity(new Intent(CheckInCheckOut.this, Attendance.class));
    }

    public void showAttendance(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void logout(View View) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("rollNumber", "");
        editor.apply();
        startActivity(new Intent(CheckInCheckOut.this, LoginActivity.class));
    }

}