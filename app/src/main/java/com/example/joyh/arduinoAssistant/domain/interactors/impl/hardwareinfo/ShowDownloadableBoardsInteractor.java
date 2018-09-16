package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;

import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 */

public interface ShowDownloadableBoardsInteractor extends Interactor {
    interface Callback{
        void onShowDownLoadableBoardsError(String err);
        void onShowDownloadableBoards(List<BoardBeanModel> boards);
        void onNoDownloadableBoard();

        void onShowProgress();
        void onHideProgress();
    }
    List<BoardBeanModel>  showDownloadableBoards();
    void refreshDownloadList();
}
