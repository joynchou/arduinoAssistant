package com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.RecommendListInteractor;

/**
 * Created by joyn on 2018/9/17 0017.
 */

public class RecommendListInteractorImpl extends AbstractInteractor implements RecommendListInteractor {
    public RecommendListInteractorImpl(Executor threadExecutor, MainThread mainThread) {
        super(threadExecutor, mainThread);
    }

    @Override
    public void run() {

    }
}
