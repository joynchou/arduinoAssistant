package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aitangba.swipeback.SwipeBackActivity;
import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.apiinfo.ArduinoFragment;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter.TabFragmenPagerAdapter;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.fragment.DownloadableBoardFragment;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.fragment.DownloadedBoardFragment;
import com.githang.statusbar.StatusBarCompat;

/**
 * Created by joyn on 2018/8/22 0022.
 */

public class BoardManagerActivity extends SwipeBackActivity {

    private ProgressBar progressBar;
    private TextView info;
    private FloatingActionButton floatingActionButton;
    private BoardRepositoryImpl boardRepository;
    private int sysVersion = Integer.parseInt(Build.VERSION.SDK);
    private ViewPager viewPager;
    private TabFragmenPagerAdapter fragmenPagerAdapter;
    private TabLayout tabLayout;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_board_downloader);

        initToorbor();
        progressBar = findViewById(R.id.progressBar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);

        final DownloadableBoardFragment downloadableBoardFragment = new DownloadableBoardFragment();
        final DownloadedBoardFragment downloadedBoardFragment = new DownloadedBoardFragment();
        ArduinoFragment textFragment1 = new ArduinoFragment();
        ArduinoFragment textFragment2 = new ArduinoFragment();


        fragmenPagerAdapter = new TabFragmenPagerAdapter(getSupportFragmentManager());
        fragmenPagerAdapter.addFragment(downloadableBoardFragment, "可下载的开发板");
        fragmenPagerAdapter.addFragment(downloadedBoardFragment, "已下载的开发板");
        viewPager.setAdapter(fragmenPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:

                        break;
                    case 1:
                        downloadedBoardFragment.onResume();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_downloader, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_refresh:
                switch (tabLayout.getSelectedTabPosition()) {

                    case 0:
                        break;
                    case 1:
                        break;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //以下为自定义私有方法
    private void showInfo(String message) {
        Toast.makeText(BoardManagerActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void initToorbor() {

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), false);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("下载器");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
