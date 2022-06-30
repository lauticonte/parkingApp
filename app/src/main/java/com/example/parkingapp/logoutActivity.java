package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class logoutActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
        mAuth.signOut();
        goToLogin();
    }

//    public void logout (View view){
//        mAuth = FirebaseAuth.getInstance();
//        Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
//        mAuth.signOut();
//        goToLogin();
//        finish();
//    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

