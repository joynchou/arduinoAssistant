package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;

import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 */

public interface HardwareInfoShowDownloadableBoardsInteractor extends Interactor {
    interface Callback{
        void onShowDownLoadableBoardsError(String err);
        void onShowDownloadableBoards(List<BoardBeanModelImpl> boards);
        void onNoDownloadableBoard();

        void onShowProgress();
        void onHideProgress();
    }
    List<BoardBeanModelImpl>  showDownloadableBoards();
    void refreshDownloadList();
}
