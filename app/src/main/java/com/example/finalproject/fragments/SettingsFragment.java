package com.example.finalproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.ImageActivity;
import com.example.finalproject.InfoActivity;
import com.example.finalproject.R;
import com.example.finalproject.RegisterInfo.RegisterImage;
import com.example.finalproject.SettingActivity;
import com.example.finalproject.StartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class SettingsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private LinearLayout image_button,info_button,setting_button;
    private TextView profile_name_age, profile_school;
    private ImageView profile_image;
    private String currentUid;
    int nowYear;
    private DatabaseReference userDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String birthDay = snapshot.child("birthDay").getValue().toString();
                    int year = Integer.parseInt(birthDay.substring(birthDay.length() - 4, birthDay.length()));
                    String age = String.valueOf(nowYear - year);
                    profile_name_age.setText(name+","+" "+age);
                    profile_school.setText(snapshot.child("school").getValue().toString());
                    Glide.with(getContext()).load(snapshot.child("profileImageUrl").getValue().toString()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nowYear = Calendar.getInstance().get(Calendar.YEAR);
        mAuth = FirebaseAuth.getInstance();
        Button signOut = view.findViewById(R.id.signOut);
        profile_image = view.findViewById(R.id.profile_image);
        profile_name_age = view.findViewById(R.id.profile_name_age);
        profile_school = view.findViewById(R.id.profile_school);
        image_button = view.findViewById(R.id.image_button);
        info_button = view.findViewById(R.id.info_button);
        setting_button = view.findViewById(R.id.setting_button);
        currentUid = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid);

        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String birthDay = snapshot.child("birthDay").getValue().toString();
                    int year = Integer.parseInt(birthDay.substring(birthDay.length() - 4, birthDay.length()));
                    String age = String.valueOf(nowYear - year);
                    profile_name_age.setText(name+","+" "+age);
                    Glide.with(getContext()).load(snapshot.child("profileImageUrl").getValue().toString()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        image_button.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), ImageActivity.class));
        });
        info_button.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), InfoActivity.class));
        });
        setting_button.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });
        signOut.setOnClickListener(view1 -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), StartActivity.class));
            getActivity().finish();
        });
    }
}