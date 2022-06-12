package com.example.finalproject.RegisterInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.utils.VerticalSpaceItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;


public class RegisterHobbies extends AppCompatActivity {
    RecyclerView recyclerView;
    ItemClickListener itemClickListener;
    MainAdapter adapter;
    ArrayList<String> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hobbies);

        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<String> arrayList = new ArrayList<String>(
                Arrays.asList("yoga", "truyện tranh", "bóng đá", "xem phim", "ăn tối", "gym", "k-pop", "mạng xã hội",
                        "võ", "chạy bộ", "hip hop", "karaoke", "xăm", "hội họa", "nghệ thuật", "trao đổi ngôn ngữ",
                        "làm đẹp", "thời trang", "đọc sách", "cầu lông", "mô tô", "văn thơ", "anime", "nhảy")
        );


        results = new ArrayList<>();

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(String s) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                results.add(s);
            }

            @Override
            public void onRemove(String s) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                results.remove(s);
            }
        };

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(9,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(13,20));
        adapter = new MainAdapter(arrayList,itemClickListener);
        recyclerView.setAdapter(adapter);


        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });
        Button confirm = findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(view -> {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("hobbies");
            db.setValue(results.toString());

            Intent intent = new Intent(RegisterHobbies.this, RegisterSchool.class);
            startActivity(intent);
        });
    }

}