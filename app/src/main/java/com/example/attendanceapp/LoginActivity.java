package com.example.attendanceapp;

import android.Manifest;
import android.content.Intent;

import androidx.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText rollNumber, password;
    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
        } else {
            requestPermissions(new String[] { Manifest.permission.CAMERA },
                    0);
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
        } else {
            // You can directly ask for the permission.
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    0);
        }
        myDb = new DatabaseHelper(this);
        if (!sharedpreferences.getString("rollNumber", "").equals("")){
            startActivity(new Intent(LoginActivity.this, CheckInCheckOut.class));
            finish();
        }
//        getLocation();
    }

//    public void getLocation(){
//        GpsTracker gpsTracker = new GpsTracker(LoginActivity.this);
//        if(gpsTracker.canGetLocation()){
//            double latitude = gpsTracker.getLatitude();
//            double longitude = gpsTracker.getLongitude();
//            String tvLatitude = String.valueOf(latitude);
//            String tvLongitude = String.valueOf(longitude);
////            Toast.makeText(this,tvLatitude, Toast.LENGTH_LONG).show();
////            Toast.makeText(this,tvLongitude, Toast.LENGTH_LONG).show();
//        }else{
//            gpsTracker.showSettingsAlert();
//        }
//    }

    public void login(View View) {
        rollNumber = (EditText) findViewById(R.id.rollNumberEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        boolean isUser = false;
        Cursor res = myDb.authenticateUser(rollNumber.getText().toString(), password.getText().toString());
            if (res.getCount() > 0) {
            while (res.moveToNext()) {
                if (res.getString(2).equals(rollNumber.getText().toString()) && res.getString(3).equals(password.getText().toString()))
                    isUser = true;
            }
        }
        if (isUser) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("rollNumber", rollNumber.getText().toString());
            editor.apply();
            startActivity(new Intent(LoginActivity.this, CheckInCheckOut.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Roll number and password don't match", Toast.LENGTH_SHORT).show();
        }
    }

    public void signUp(View View) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        finish();
    }

}