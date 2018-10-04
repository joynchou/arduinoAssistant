package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;

/**
 * Created by joyn on 2018/9/3 0003.
 * 用例3：收藏板子
 * 由用户触发
 */

public interface BoardCollectionInteractor extends Interactor {
    interface Callback{
        /**
         * 错误回调函数
         * @param error 错误信息
         */
        void onError(String error);

        /**
         * 收藏状态改变回调函数
         * @param state 改变后的状态
         */
        void onCollectionStateChanged(CollectionModel model,boolean state);
    }
    void usecaseChangCollectionState(CollectionModel collectionModel, boolean state);
    void usecaseStarButtonClicked(BoardBeanModel board);

}
