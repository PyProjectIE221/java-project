package com.example.finalproject.RegisterInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


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
        ArrayList<String> arrayList = new ArrayList<>();

        for(int i=0;i<30;i++){
            arrayList.add("Button "+i);
        }

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
                Toast.makeText(getApplicationContext(), "Selected"+s, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Unselected"+s, Toast.LENGTH_SHORT).show();
            }
        };

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
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
            for(String result : results){
                db.child(result).setValue(true);
            }
            Intent intent = new Intent(RegisterHobbies.this, RegisterImage.class);
            startActivity(intent);
        });
    }

}