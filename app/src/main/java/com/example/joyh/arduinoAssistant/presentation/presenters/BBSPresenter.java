package com.example.joyh.arduinoAssistant.presentation.presenters;

import com.example.joyh.arduinoAssistant.presentation.presenters.base.BasePresenter;
import com.example.joyh.arduinoAssistant.presentation.ui.BaseView;

/**
 * Created by joyn on 2018/8/6 0006.
 */

public interface BBSPresenter extends BasePresenter {

    interface View extends BaseView {
        // TODO: Add your view methods
        void showURL(String url);
        void forward();
        void previous();
        void refresh();
    }
    void openArduinoWebsite();
}
