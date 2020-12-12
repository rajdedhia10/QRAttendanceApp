package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText nameEditText, rollNumberEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myDb = new DatabaseHelper(this);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        rollNumberEditText = (EditText) findViewById(R.id.rollNumberEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    }

    public void signUp(View View) {
        boolean isSignedUp = myDb.insertDataInLogin(nameEditText.getText().toString(),
                rollNumberEditText.getText().toString(),
                passwordEditText.getText().toString());
        if (isSignedUp) {
            Toast.makeText(SignUpActivity.this, "Signed Up", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        } else
            Toast.makeText(SignUpActivity.this, "Failed to sign up", Toast.LENGTH_SHORT).show();
    }

}