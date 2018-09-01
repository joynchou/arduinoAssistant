package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;

import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 */

public interface HardwareInfoShowAvailableBoardsInteractor extends Interactor {
    interface Callback{
        void onAvailableBoard(List<BoardBeanModelImpl> boards);
        void onShowAvailableBoardsError(String err);
        void onShowProgress();
        void onHideProgress();
    }
    void showAvailableBoard();
}
