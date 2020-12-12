package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.text.DecimalFormat;

public class Scan extends AppCompatActivity {
    DatabaseHelper myDb;
    String rollNumber, classText, time;
    private SharedPreferences sharedpreferences;

    public double[] getLocation() {
        GpsTracker gpsTracker = new GpsTracker(Scan.this);
        double[] myList = new double[0];
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            myList = new double[2];
            DecimalFormat df = new DecimalFormat("0.0");
            myList[0] = Double.parseDouble(df.format(latitude));
            myList[1] = Double.parseDouble(df.format(longitude));
            Toast.makeText(this, String.valueOf(myList[0]), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, String.valueOf(myList[1]), Toast.LENGTH_SHORT).show();
        } else {
            gpsTracker.showSettingsAlert();
        }
        return myList;
    }

    public CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        setContentView(R.layout.activity_scan);
        double[] returned = getLocation();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        rollNumber = sharedpreferences.getString("rollNumber", "");

//        Toast.makeText(this, returned[0], Toast.LENGTH_LONG).show();
        if (returned[0] == 19.2 && returned[1] == 73.1) {
//        if (true) {
            CodeScannerView scannerView = findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(this, scannerView);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Scan.this, result.getText(), Toast.LENGTH_SHORT).show();
                            classText = result.getText().split(",")[0];
                            time = result.getText().split(",")[1];
                            myDb.insertDataInAttendance(rollNumber, classText, time);
                            startActivity(new Intent(Scan.this, CheckInCheckOut.class));
                            finish();
                        }
                    });
                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });

        } else {
            Toast.makeText(Scan.this, "Your location does not match with college", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Scan.this, CheckInCheckOut.class));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
//    }
    }
}


