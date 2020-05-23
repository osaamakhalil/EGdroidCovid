package com.example.covid.adapter;


import android.util.Pair;

import com.example.covid.world.WorldFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private List<Pair<String, Fragment>> fragments;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, List fragments) {
        super(fm, behavior);
        this.fragments = fragments;

        Pair<String, Fragment> pair = new Pair<>("World", new WorldFragment());


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).second;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).first;
    }
}

