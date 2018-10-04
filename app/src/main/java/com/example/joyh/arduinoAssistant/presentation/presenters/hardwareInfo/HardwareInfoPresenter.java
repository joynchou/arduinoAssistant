package com.example.joyh.arduinoAssistant.presentation.presenters.hardwareInfo;

import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.BasePresenter;
import com.example.joyh.arduinoAssistant.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 */

public interface HardwareInfoPresenter extends BasePresenter{

    //在ui中实现的回调
    interface View extends BaseView{
        void onShowNoAvailableBoard();
        void onShowBoards(List<BoardBeanModel> boards, List<Boolean> collectionState );
        void onOpenBoardDownloadManager();
        void onViewChangeCollectionState(String boardName,boolean state);
        void onViewShowBoardDetailList(BoardBeanModel detailList);
    }
    void presenterCollectBoard(String boardName);
    void presenterStarButtonClicked(BoardBeanModel board);
    void presenterCardClicked(String boardName);
}
