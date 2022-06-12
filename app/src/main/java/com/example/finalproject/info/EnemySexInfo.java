package com.example.finalproject.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.RegisterInfo.RegisterEnemySex;
import com.example.finalproject.RegisterInfo.RegisterSex;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EnemySexInfo extends AppCompatActivity {
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enemy_sex_info);

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
            String sex = radioButton.getText().toString();
            Map userInfo = new HashMap();
            userInfo.put("enemySex",sex);
            try {
                db.updateChildren(userInfo);
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
            finish();
        });
    }
}