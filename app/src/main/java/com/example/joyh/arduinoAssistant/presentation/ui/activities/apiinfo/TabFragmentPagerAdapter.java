package com.example.joyh.arduinoAssistant.presentation.ui.activities.apiinfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyn on 2018/8/10 0010.
 * 这个不是懒加载
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mlist=new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mlist = mlist;
    }
    public void addFragment(Fragment fragment ,String title) {
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
