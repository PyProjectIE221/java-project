package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            final FirebaseUser user = mAuth.getCurrentUser();
            if(user != null){
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                },2000);
            }else{
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(SplashScreen.this, StartActivity.class));
                    finish();
                },2000);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}