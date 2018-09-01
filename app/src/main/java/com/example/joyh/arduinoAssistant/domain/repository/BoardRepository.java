package com.example.joyh.arduinoAssistant.domain.repository;

import com.example.joyh.arduinoAssistant.domain.model.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;

import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 */

public interface BoardRepository {

    int ENTRY_LEVEL = 0;
    int ENHANCED_FEATURES = 1;
    int RETIRED = 2;
    interface Callback{
        void onError(String error);
    }
    //取得已经下载的可用的板子数目
    int getAvailableBoardAmount();
    //取得已经下载的可用的板子
    List<BoardBeanModelImpl> getAvailableBoards();
    String getArduinoDeviceWebsite();
    String boardDownloadSavePath(String toSavedBoardName);
    String boardDownloadDeletePath(String toDeletedBoardName);
    void addBoardResource(BoardBeanModel board);
    void deleteBoardResource(String boardName);
    void queryBoardResource(String boradName);

    List<String> getDownloadableBoardName(int boardLevel);
    List<String> getDownloadableBoardImgURL(int boardLevel);
    String getBoardDetailURL(String boardname);
    List<String> getAllResource(String boardURL);

}
