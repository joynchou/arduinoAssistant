package com.example.joyh.arduinoAssistant.domain.interactors.impl.APIInfo;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;

/**
 * Created by joyn on 2018/8/12 0012.
 */

public class APIInfoInteractorImpl  extends AbstractInteractor implements APIInfoInteractor {

    public APIInfoInteractorImpl(Executor threadExecutor, MainThread mainThread,Callback callback) {
        super(threadExecutor, mainThread);
        Callback callback1 = callback;
    }

    @Override
    public void run() {

    }
}
