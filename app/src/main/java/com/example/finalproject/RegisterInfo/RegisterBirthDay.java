package com.example.finalproject.RegisterInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterBirthDay extends AppCompatActivity {
    private final Calendar mCalendar = Calendar.getInstance();
    private EditText mBirthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_birth_day);

        mBirthday = findViewById(R.id.Birthday);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            mCalendar.set(Calendar.YEAR,i);
            mCalendar.set(Calendar.MONTH,i1);
            mCalendar.set(Calendar.DAY_OF_MONTH,i2);
            updateLabel();
        };
        mBirthday.setOnClickListener(view -> new DatePickerDialog(RegisterBirthDay.this, AlertDialog.THEME_HOLO_LIGHT,date,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show());

        Button confirm = findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(view -> {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            String birthDay = mBirthday.getText().toString();
            int nowYear = Calendar.getInstance().get(Calendar.YEAR);
            int year = Integer.parseInt(birthDay.substring(birthDay.length()-4,birthDay.length()));
            if(nowYear - year > 15) {
                Map userInfo = new HashMap();
                userInfo.put("birthDay", birthDay);
                db.updateChildren(userInfo);
                Intent intent = new Intent(RegisterBirthDay.this, RegisterSex.class);
                startActivity(intent);
            }else{
                Toast.makeText(RegisterBirthDay.this, "Ngày sinh bạn nhập có số tuổi nhỏ hơn 16", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.TAIWAN);
        mBirthday.setText(dateFormat.format(mCalendar.getTime()));
    }
}