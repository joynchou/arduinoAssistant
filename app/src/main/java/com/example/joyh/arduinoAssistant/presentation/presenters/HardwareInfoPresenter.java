package com.example.joyh.arduinoAssistant.presentation.presenters;

import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
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
        void onShowBoards(List<BoardBeanModelImpl> boards);
        void onOpenBoardDownloadManager();
    }
}
