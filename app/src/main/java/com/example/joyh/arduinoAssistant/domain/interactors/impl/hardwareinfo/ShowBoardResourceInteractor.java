package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.SharableBeanModel;

/**
 * Created by joyn on 2018/9/13 0013.
 * 用例：打开某个板子的详细资源界面
 */

public interface ShowBoardResourceInteractor extends Interactor {
    interface Callback{
        void onShowBoardResource(String boardName,int resourceType);
        void onShowBoardResourceError(String error);
        /**
         * 分享可分享的资源回调
         * @param sharable 可分享对象
         */
        void onShareResource(SharableBeanModel sharable);
    }
    /**
     * 分享资源
     * @param sharable 可分享的对象
     */
    void InteractorShareResource(SharableBeanModel sharable);
    void InteractorShowBoardResource(String boardname,int resourceType);

}
