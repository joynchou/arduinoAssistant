package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyn on 2018/8/18 0018.
 *
 */

class TabFragmenPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mlist=new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public TabFragmenPagerAdapter(FragmentManager fm, List<Fragment> mlist) {
        super(fm);
        this.mlist = mlist;
    }

    public void addFragment(Fragment fragment , String title) {
        mlist.add(fragment);
        mFragmentTitles.add(title);

    }
    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
