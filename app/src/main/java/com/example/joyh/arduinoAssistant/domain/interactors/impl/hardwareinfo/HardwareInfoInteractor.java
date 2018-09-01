package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;


/**
 * Created by joyn on 2018/8/21 0021.
 */

public interface HardwareInfoInteractor extends Interactor {
    interface Callback {
        void onNoBoardAvailable();
        void onOpenBoardDownloadManager();
    }

    void showBoards();
    void noBoardAvailabel();
}
