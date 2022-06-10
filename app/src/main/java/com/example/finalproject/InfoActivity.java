package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.RegisterInfo.RegisterHobbies;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    private EditText mIntroduce, mName, mSchool;
    private TextView mHobbies,mUserSex, mEnemySex;
    private Button btnBack, confirm;
    private FirebaseAuth mAuth;
    private String userId;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        btnBack = findViewById(R.id.btnBack);
        mIntroduce = findViewById(R.id.introduce);
        mName = findViewById(R.id.name);
        mHobbies = findViewById(R.id.hobbies);
        mSchool = findViewById(R.id.school);
        mUserSex = findViewById(R.id.userSex);
        mEnemySex = findViewById(R.id.enemySex);
        confirm = findViewById(R.id.confirm);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    mName.setText(snapshot.child("name").getValue().toString());
                    mHobbies.setText(snapshot.child("hobbies").getValue().toString());
                    mIntroduce.setText(snapshot.child("introduce").getValue().toString());
                    mSchool.setText(snapshot.child("school").getValue().toString());
                    mUserSex.setText(snapshot.child("userSex").getValue().toString());
                    mEnemySex.setText(snapshot.child("enemySex").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnBack.setOnClickListener(view -> {
            finish();
        });

        confirm.setOnClickListener(view ->{
            String introduce = mIntroduce.getText().toString();
            String school = mSchool.getText().toString();
            String hobbies = mHobbies.getText().toString();
            String userSex = mUserSex.getText().toString();
            String enemySex = mEnemySex.getText().toString();
        });


    }
}