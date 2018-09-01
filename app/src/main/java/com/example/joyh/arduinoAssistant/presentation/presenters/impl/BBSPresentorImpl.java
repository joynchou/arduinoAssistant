package com.example.joyh.arduinoAssistant.presentation.presenters.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.presentation.presenters.BBSPresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.AbstractPresenter;

/**
 * Created by joyn on 2018/8/6 0006.
 */

public class BBSPresentorImpl extends AbstractPresenter implements BBSPresenter {
    public BBSPresentorImpl(Executor executor,
                                MainThread mainThread,
                            View view) {

        super(executor, mainThread);
        View mView = view;


    }

    @Override
    public void openArduinoWebsite() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
