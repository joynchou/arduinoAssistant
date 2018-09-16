package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter;

/**
 * Created by joyn on 2018/9/3 0003.
 */

public interface AvailableBoardsRecyclerViewAdapterInterface {
    interface Callback{
        void onStarButtonClicked(String boardName);
        void onCardClicked(String boardName);
    }
}
