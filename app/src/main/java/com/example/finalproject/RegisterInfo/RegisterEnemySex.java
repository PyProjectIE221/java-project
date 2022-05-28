package com.example.finalproject.RegisterInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterEnemySex extends AppCompatActivity {

    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_enemy_sex);

        radioGroup = findViewById(R.id.radioGroup);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });


        Button confirm = findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(view -> {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            int checkedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(checkedId);
            String enemySex = radioButton.getText().toString();
            Map userInfo = new HashMap();
            userInfo.put("enemySex",enemySex);
            db.updateChildren(userInfo);
            Intent intent = new Intent(RegisterEnemySex.this, RegisterHobbies.class);
            startActivity(intent);
        });
    }
}