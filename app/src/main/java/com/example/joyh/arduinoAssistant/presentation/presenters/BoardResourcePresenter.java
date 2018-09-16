package com.example.joyh.arduinoAssistant.presentation.presenters;

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
    }
}
