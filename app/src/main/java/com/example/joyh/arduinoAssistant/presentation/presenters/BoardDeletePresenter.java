package com.example.joyh.arduinoAssistant.presentation.presenters;

import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.BasePresenter;
import com.example.joyh.arduinoAssistant.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by joyn on 2018/9/8 0008.
 */

public interface BoardDeletePresenter extends BasePresenter {

    interface View extends BaseView{
        void onViewDeleteBoard(String boardName);
        void onViewNoDownloadedBoard();
        void onViewshowDownloadedBoards(List<BoardBeanModel> boards);
    }
    void deleteBoard(String name);
}
