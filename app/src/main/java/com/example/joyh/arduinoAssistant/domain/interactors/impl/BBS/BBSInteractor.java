package com.example.joyh.arduinoAssistant.domain.interactors.impl.BBS;

/**
 * Created by joyn on 2018/8/6 0006.
 */

interface BBSInteractor {
    interface Callback{

    }
    void openURL(String url);
    void forward();
    void previous();
    void refresh();
}
