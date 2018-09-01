/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.joyh.arduinoAssistant.presentation.ui.activities.homepage.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baidu.mobstat.StatService;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.apiinfo.APIInfoActivity;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.about.aboutSoftWareActivity;
import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.HardWareInfoActivity;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.setting.settingFragment;
import com.githang.statusbar.StatusBarCompat;

/**
 * 主页界面
 */
public class homePageActivity extends AppCompatActivity implements IHomePageView {
    //侧边菜单栏
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatService.start(this);
        // 获取测试设备ID
        String testDeviceId = StatService.getTestDeviceId(this);
        // 日志输出
        android.util.Log.d("BaiduMobStat", "Test DeviceId : " + testDeviceId);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary),false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("主页");
        //设置顶部工具条
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    //顶部工具条被选中的复写
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            case R.id.menu_setting://设置按钮
                android.app.FragmentManager fragmentManager = getFragmentManager();
                settingFragment settingFragment=new settingFragment();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               // fragmentTransaction
               //         .replace(R.id.frameLayout ,settingFragment);
                fragmentTransaction.addToBackStack(null);
              //  fragmentTransaction.commit();


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }


    /* 设置左边抽屉菜单上下文 */
    private void setupDrawerContent(NavigationView navigationView) {
        final View nav = navigationView;
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.about_software:
                                intent = new Intent(homePageActivity.this, aboutSoftWareActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_API_info:
                                intent = new Intent(homePageActivity.this, APIInfoActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_BBS:
                                //intent = new Intent(homePageActivity.this, BBSActivity.class);
                                //startActivity(intent);
                                break;
                            case R.id.nav_hardware_info:
                                intent = new Intent(homePageActivity.this, HardWareInfoActivity.class);
                                startActivity(intent);
                        }

                        return true;
                    }
                });
    }


}
