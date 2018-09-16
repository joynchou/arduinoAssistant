package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;

import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 */

public interface ShowAvailableBoardsInteractor extends Interactor {
    interface Callback{
        void onAvailableBoard(List<BoardBeanModel> boards, List<Boolean> collectionState);
        void onShowAvailableBoardsError(String err);
        void onShowProgress();
        void onHideProgress();
        void onOpenBoardDetailList(BoardBeanModel board);
    }
    void showAvailableBoard();
    /**
     * 展示板子的资源列表
     * @param board 板子对象
     */
    void InteractorOpenBoardDetailList(BoardBeanModel board);
}
