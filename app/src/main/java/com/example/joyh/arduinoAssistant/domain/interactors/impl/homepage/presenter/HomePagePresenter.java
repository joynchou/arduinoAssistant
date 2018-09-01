package com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.presenter;

import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.model.HomePageModel;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.model.IHomePageModel;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.homepage.view.IHomePageView;

/**
 * Created by joyn on 2018/7/30 0030.
 */

class HomePagePresenter {

    public HomePagePresenter(IHomePageView view)
    {
        IHomePageView mHomePageView = view;
        IHomePageModel mHomePageModel = new HomePageModel();
    }

}
