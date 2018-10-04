package com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.HistoryDeletion;

/**
 * Created by joyn on 2018/9/17 0017.
 */

public class HistoryDeletionImpl extends AbstractInteractor implements HistoryDeletion {
    public HistoryDeletionImpl(Executor threadExecutor, MainThread mainThread) {
        super(threadExecutor, mainThread);
    }

    @Override
    public void run() {

    }
}
