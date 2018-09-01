package com.example.joyh.arduinoAssistant.presentation.ui.activities.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aitangba.swipeback.SwipeBackActivity;
import com.example.joyh.arduinoAssistant.R;
import com.githang.statusbar.StatusBarCompat;

/**
 * Created by joyn on 2018/7/27 0027.
 * 软件关于界面
 */

public class aboutSoftWareActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_software);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary),false);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("关于");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout);
        //collapsingToolbarLayout.setTitle("关于");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
