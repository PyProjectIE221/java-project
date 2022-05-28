package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button register = findViewById(R.id.btnRegister);
        Button login = findViewById(R.id.btnLogin);

        register.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this,RegisterActivity.class);
            startActivity(intent);
        });

        login.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this,LoginActivity.class);
            startActivity(intent);
        });
    }
}