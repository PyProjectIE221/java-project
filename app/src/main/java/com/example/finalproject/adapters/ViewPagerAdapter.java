package com.example.finalproject.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.finalproject.fragments.ChattingFragment;
import com.example.finalproject.fragments.SettingsFragment;
import com.example.finalproject.fragments.SwipeFragment;

import java.util.List;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;
    public ViewPagerAdapter(@NonNull List<Fragment> fragmentList,FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position >= 0 && position < fragmentList.size()) {
            return fragmentList.get(position);
        }

        return new Fragment();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
