package com.example.adapter;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;

import com.example.Fragment.ChartFragment1;
import com.example.Fragment.ChartFragment2;
public class ChartPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 2;

    public ChartPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ChartFragment1();
            case 1:
                return new ChartFragment2();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}