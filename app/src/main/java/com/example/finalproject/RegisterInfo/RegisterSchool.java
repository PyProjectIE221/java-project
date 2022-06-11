package com.example.finalproject.RegisterInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.info.SchoolActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterSchool extends AppCompatActivity {
    private TextView mSchool;
    private ImageView back;
    private Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_school);

        back = findViewById(R.id.btnBack);
        mSchool = findViewById(R.id.school);
        confirm = findViewById(R.id.btnConfirm);
        back.setOnClickListener(view -> {
            finish();
        });

        List<String> schools = new ArrayList<String>(
                Arrays.asList("Trường Đại học Công Nghệ Thông Tin","Trường Đại học Khoa học xã hội và Nhân văn",
                        "Trường Đại học Bách Khoa","Trường Đại học Khoa học Tự nhiên","Trường Đại học Quốc tế",
                        "Trường Đại học Sư phạm Kỹ thuật TP.HCM","Trường Đại học Tôn Đức Thắng","Trường Đại học Y khoa Phạm Ngọc Thạch",
                        "Công Ty TNHH Phần Mềm FPT Hồ Chí Minh","Công Ty Cổ Phần Fujinet Systems","Công Ty Global CyberSoft Việt Nam",
                        "Intel Corporation","Công ty cổ phần VNG","Công nghệ Bosch Rexroth","Tập đoàn công nghệ Viettel")
        );

        mSchool.setOnClickListener(view -> {
            SchoolActivity schoolActivity = new SchoolActivity(schools, new ItemClickListener() {
                @Override
                public void onClick(String s) {
                    mSchool.setText(s);
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    Map userInfo = new HashMap();
                    userInfo.put("school",mSchool.getText().toString());
                    try {
                        db.updateChildren(userInfo);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onRemove(String s) {

                }
            });
            schoolActivity.show(getSupportFragmentManager(),schoolActivity.getTag());
        });

        confirm.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterSchool.this, RegisterImage.class);
            startActivity(intent);
        });
    }
}