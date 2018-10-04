package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter;

import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;

/**
 * Created by joyn on 2018/9/3 0003.
 */

public interface AvailableBoardsRecyclerViewAdapterInterface {
    interface Callback{
        void onStarButtonClicked(BoardBeanModel boardName);
        void onCardClicked(String boardName);
    }
}
