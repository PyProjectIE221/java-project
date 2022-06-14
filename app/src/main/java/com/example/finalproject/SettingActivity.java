package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.RegisterInfo.RegisterPhone;
import com.google.firebase.auth.FirebaseAuth;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    SeekBar seekBarDis, seekBarAge;
    TextView textViewDis, textViewAge, phone;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private String regexStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        regexStr = "^[0-9]{10,13}$";
        seekBarDis = findViewById(R.id.SeekBarID);
        textViewDis = findViewById(R.id.distanceID);
        phone = findViewById(R.id.phone);
        Button signOut = findViewById(R.id.signOut);
        Button back = findViewById(R.id.btnBack);
        seekBarAge = findViewById(R.id.SeekBarAgeID);
        textViewAge = findViewById(R.id.ageID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBarDis.setMin(0);
            seekBarAge.setMin(16);
        }
        seekBarDis.setMax(1000);
        seekBarAge.setMax(100);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            db = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    seekBarDis.setProgress(Integer.parseInt(snapshot.child("distance").getValue().toString()));
                    seekBarAge.setProgress(Integer.parseInt(snapshot.child("enemyAge").getValue().toString()));
                    phone.setText(snapshot.child("phone").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        back.setOnClickListener(view1 ->{
            finish();
        });

        seekBarDis.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewDis.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Map userInfo = new HashMap();
                userInfo.put("distance",textViewDis.getText().toString());
                db.updateChildren(userInfo);
            }

        });
        signOut.setOnClickListener(view1 -> {
            mAuth.signOut();
            startActivity(new Intent(this, StartActivity.class));
            finish();
        });


        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewAge.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Map userInfo = new HashMap();
                userInfo.put("enemyAge",textViewAge.getText().toString());
                db.updateChildren(userInfo);
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(phone.getText().toString().matches(regexStr)==false){
                    Toast.makeText(SettingActivity.this, "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
                }else {
                    Map userInfo = new HashMap();
                    userInfo.put("phone", phone.getText().toString());
                    db.updateChildren(userInfo);
                }
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (phone.isFocused()) {
                Rect outRect = new Rect();
                phone.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    phone.clearFocus();
                    InputMethodManager imm = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    }
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}