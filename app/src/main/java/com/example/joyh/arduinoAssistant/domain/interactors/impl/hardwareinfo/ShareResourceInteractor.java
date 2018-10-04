package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;

/**
 * Created by joyn on 2018/9/17 0017.
 * 用例9、分享资源
 */

public interface ShareResourceInteractor extends Interactor{

    interface Callback{
        void onShareResource(String resourceName,int resourceType);

    }

    /**
     * 分享资源
     * @param resourceName
     * @param resourceType
     */
    void interactorShareResource(String resourceName, int resourceType);
    void InteractorShareClicked(BoardBeanModel boardBeanModel);
}
