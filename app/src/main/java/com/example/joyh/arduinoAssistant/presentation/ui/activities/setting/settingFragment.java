package com.example.joyh.arduinoAssistant.presentation.ui.activities.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import android.support.v7.widget.Toolbar;
import com.example.joyh.arduinoAssistant.R;

/**
 * Created by joyn on 2018/8/5 0005.
 */

public class settingFragment extends PreferenceFragment {
    //保存之前的appbar的title
    private String  title;
    //toolbar的字体设置
    private Toolbar toolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundResource(R.color.window_background);
        toolbar=getActivity().findViewById(R.id.toolbar);
        title=toolbar.getTitle().toString();
        toolbar.setTitle("设置");
        //设置顶部工具条


        return view;
    }
//fragment is no longer visible to the user either because its activity
// is being stopped or a fragment operation is modifying it in the activity.
    @Override
    public void onStop() {
        super.onStop();
        toolbar.setTitle(title);

    }
}
