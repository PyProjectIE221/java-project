package com.example.finalproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.info.AddressInfo;
import com.example.finalproject.info.EnemySexInfo;
import com.example.finalproject.info.HobbiesInfo;
import com.example.finalproject.info.SchoolInfo;
import com.example.finalproject.info.UserSexInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class InfoActivity extends AppCompatActivity {
    private EditText mIntroduce, mName;
    private TextView mHobbies,mUserSex, mEnemySex, mSchool, mBirthDay, mAddress;
    private Button btnBack, confirm;
    private FirebaseAuth mAuth;
    private String userId;
    private DatabaseReference db;
    private Geocoder geocoder;
    private final Calendar mCalendar = Calendar.getInstance();

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
        mBirthDay = findViewById(R.id.Birthday);
        mAddress = findViewById(R.id.address);
        geocoder = new Geocoder(this,Locale.getDefault());

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
                    mBirthDay.setText(snapshot.child("birthDay").getValue().toString());
//                    Double lat = Double.valueOf(Objects.requireNonNull(snapshot.child("lat").getValue()).toString());
//                    Double lon = Double.valueOf(Objects.requireNonNull(snapshot.child("long").getValue()).toString());
//                    try {
//                        List<Address> addresses = geocoder.getFromLocation(lat,lon,1);
//                        mAddress.setText(addresses.get(0).getAddressLine(0));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    mAddress.setText(snapshot.child("address").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHobbies.setOnClickListener(view -> {
            startActivity(new Intent(this, HobbiesInfo.class));
        });

        mUserSex.setOnClickListener(view ->{
            startActivity(new Intent(this, UserSexInfo.class));
        });

        mEnemySex.setOnClickListener(view ->{
            startActivity(new Intent(this, EnemySexInfo.class));
        });

        mSchool.setOnClickListener(view -> {
            Intent intent = new Intent(this, SchoolInfo.class);
            Bundle b = new Bundle();
            b.putString("school",mSchool.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
        });

        mAddress.setOnClickListener(view -> {
            Intent intent = new Intent(this,AddressInfo.class);
            Bundle b = new Bundle();
            b.putString("address",mAddress.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
        });

        btnBack.setOnClickListener(view -> {
            finish();
        });

        // Change birth day

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            mCalendar.set(Calendar.YEAR,i);
            mCalendar.set(Calendar.MONTH,i1);
            mCalendar.set(Calendar.DAY_OF_MONTH,i2);
            updateLabel();
        };
        mBirthDay.setOnClickListener(view -> new DatePickerDialog(InfoActivity.this, AlertDialog.THEME_HOLO_LIGHT,date,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show());

        confirm.setOnClickListener(view ->{
            String introduce = mIntroduce.getText().toString();
            String name = mName.getText().toString();
            Map userInfo = new HashMap();
            userInfo.put("introduce",introduce);
            userInfo.put("name",name);
            String birthDay = mBirthDay.getText().toString();
            int nowYear = Calendar.getInstance().get(Calendar.YEAR);
            int year = Integer.parseInt(birthDay.substring(birthDay.length()-4,birthDay.length()));
            if(nowYear - year > 15) {
                userInfo.put("birthDay", birthDay);
                try {
                    db.updateChildren(userInfo);
                    Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(InfoActivity.this, "Ngày sinh bạn nhập có số tuổi nhỏ hơn 16", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.TAIWAN);
        mBirthDay.setText(dateFormat.format(mCalendar.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    mName.setText(snapshot.child("name").getValue().toString());
                    mHobbies.setText(snapshot.child("hobbies").getValue().toString()
                            .replace("[","").replace("]",""));
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
    }
}