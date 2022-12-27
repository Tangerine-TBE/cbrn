package com.dangkang.cbrn.fragment.main.setting;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 ** DATE: 2022/9/27
 ** Author:tangerine
 ** Description:
 **/
public class FragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments;
    public FragmentAdapter(@NonNull  FragmentManager fm, int behavior, List<Fragment>fragments) {
        super(fm, behavior);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
