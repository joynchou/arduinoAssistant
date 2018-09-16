package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.SharableBeanModel;

/**
 * Created by joyn on 2018/9/13 0013.
 * 用例：打开某个板子的资源列表
 * 由用户触发此用例
 */

public interface ShowBoardDetailListInteractor extends Interactor {
    interface Callback{
        /**
         * 错误信息的回调
         * @param error
         */
        void onShowBoardDetailError(String error);

        /**
         * 显示板子资源列表的回调
         * @param detailList 板子对象
         */
        void onShowBoardDetailList(BoardBeanModel detailList);

        void onShowBoardCollectionState(boolean state);
    }

    /**
     * 展示板子的资源列表
     *
     */
    void InteractorShowBoardDetailList();



}
