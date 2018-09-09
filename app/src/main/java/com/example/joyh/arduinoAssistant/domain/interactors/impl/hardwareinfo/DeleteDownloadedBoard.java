package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;

/**
 * Created by joyn on 2018/9/3 0003.
 */

public interface DeleteDownloadedBoard extends Interactor {
    interface Callback{

        void onError(String error);
        void onBoardDeleted(String boardName);
    }
    void deleteBoard(String name);
}
