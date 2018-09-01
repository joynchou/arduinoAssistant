package com.example.joyh.arduinoAssistant.domain.interactors.impl.APISearch;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;

import java.util.List;

/**
 * Created by joyn on 2018/8/5 0005.
 */

public interface SearchInteractor extends Interactor {

    interface  Callback{
        void onNoResult();
        void onResults(List<String> result);

    }
    void Search(String str);
}
