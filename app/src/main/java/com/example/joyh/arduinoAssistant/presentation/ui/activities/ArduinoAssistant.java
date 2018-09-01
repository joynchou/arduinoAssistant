package com.example.joyh.arduinoAssistant.presentation.ui.activities;

import android.app.Application;
import android.content.Context;

import com.aitangba.swipeback.ActivityLifecycleHelper;

/**
 * Created by joyn on 2018/8/28 0028.
 */

public class ArduinoAssistant extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
