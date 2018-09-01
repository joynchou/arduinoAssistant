package com.example.joyh.arduinoAssistant.presentation.presenters;

import com.example.joyh.arduinoAssistant.presentation.presenters.base.BasePresenter;
import com.example.joyh.arduinoAssistant.presentation.ui.BaseView;

import java.util.List;


public interface APIInfoPresenter extends BasePresenter {

    //把view放在了presenter里
    interface View extends BaseView {
        // TODO: Add your view methods
    void showSearchEditWindow();
    void hideSearchEditWindow();
    void showNoResult();
    void showResults(List<String> result);
    }
    void search(String str);
    // TODO: Add your presenter methods


}
