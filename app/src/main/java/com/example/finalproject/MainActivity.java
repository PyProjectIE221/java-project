package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproject.adapters.ViewPagerAdapter;
import com.example.finalproject.fragments.ChattingFragment;
import com.example.finalproject.fragments.SettingsFragment;
import com.example.finalproject.fragments.SwipeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        ArrayList<Fragment> fragList = new ArrayList<Fragment>();
        fragList.add(new SettingsFragment());
        fragList.add(new SwipeFragment());
        fragList.add(new ChattingFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragList,getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(1);
        bottomNavigationView.setSelectedItemId(R.id.fire);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.account:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.fire:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.chat:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });

    }
}