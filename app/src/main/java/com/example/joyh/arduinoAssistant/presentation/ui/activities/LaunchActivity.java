package com.example.joyh.arduinoAssistant.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.homepage.view.homePageActivity;
import com.example.joyh.arduinoAssistant.R;

/**
 * Created by joyn on 2018/8/19 0019.
 * 启动界面，可以为以后添加广告做准备
 */

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载启动图片

        //后台处理耗时任务
        setContentView(R.layout.activity_launcher);
        Integer time = 1000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this, homePageActivity.class));
                LaunchActivity.this.finish();
            }
        }, time);
    }
}

