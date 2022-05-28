package com.example.finalproject.RegisterInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterPhone extends AppCompatActivity {
    private EditText mPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
        mPhone = findViewById(R.id.phone);
        Button confirm = findViewById(R.id.btnConfirm);

        confirm.setOnClickListener(view -> {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            String phone = mPhone.getText().toString();
            String regexStr = "^[0-9]{10,13}$";
            if(phone.matches(regexStr)==false) {
                Toast.makeText(RegisterPhone.this, "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
            }else{
                Map userInfo = new HashMap();
                userInfo.put("phone", phone);
                db.updateChildren(userInfo);
                Intent intent = new Intent(RegisterPhone.this, RegisterName.class);
                startActivity(intent);
            }
        });
    }
}