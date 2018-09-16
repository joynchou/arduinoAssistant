package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;

import java.util.List;

/**
 * Created by joyn on 2018/9/2 0002.
 * 用例:在下载管理器中展现已经下载好的板子，有别与主页，此界面是用来删除板子的
 * Interactor :Manage already downloaded boards
 */

public interface ShowDownloadedBoardInteractor  extends Interactor{

    interface Callback{
        void onDeleteBoard(String boardName);
        void onNoDownloadedBoard();
        void onshowDownloadedBoards(List<BoardBeanModel> boards);
        void onError(String error);
    }
    void deletaBoard(String boardName);
    void showDownloadedBoards();
    void refreshDownloadedList();
}
