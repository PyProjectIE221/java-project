package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.RegisterInfo.RegisterHobbies;

public class InfoActivity extends AppCompatActivity {
    private TextView hobbies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        hobbies = findViewById(R.id.hobbies);
        hobbies.setOnClickListener(view ->{
            startActivity(new Intent(InfoActivity.this, RegisterHobbies.class));
        });
    }
}