package com.example.joyh.arduinoAssistant.presentation.presenters.hardwareInfo;

import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.SharableBeanModel;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.BasePresenter;
import com.example.joyh.arduinoAssistant.presentation.ui.BaseView;

/**
 * Created by joyn on 2018/9/14 0014.
 */

public interface BoardResourcePresenter extends BasePresenter {
    interface View extends BaseView{


        void onViewShowResource(SharableBeanModel sharable);
        void onViewShowDetailList(BoardBeanModel beanModel);
        void onViewShowBoardCollectionState(boolean state);
        void onViewShareResource();
    }
    /**
     * 展示板子的资源列表
     *
     */
    void PresenterShowBoardDetailList(String boardName);
    void PresenterItemClicked(BoardBeanModel boardBeanModel);
    void PresenterShareClicked(BoardBeanModel boardBeanModel);
}
