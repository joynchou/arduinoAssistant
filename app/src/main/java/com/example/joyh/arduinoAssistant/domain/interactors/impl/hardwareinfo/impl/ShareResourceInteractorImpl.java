package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShareResourceInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

/**
 * Created by joyn on 2018/9/17 0017.
 */

public class ShareResourceInteractorImpl extends AbstractInteractor implements ShareResourceInteractor {
    private BoardRepository boardRepository;
    private ShareResourceInteractor.Callback callback;

    public ShareResourceInteractorImpl(Executor threadExecutor, MainThread mainThread, BoardRepository boardRepository, Callback callback) {
        super(threadExecutor, mainThread);
        this.boardRepository = boardRepository;
        this.callback = callback;
    }

    @Override
    public void run() {

    }

    @Override
    public void interactorShareResource(String resourceName, int resourceType) {

    }

    @Override
    public void InteractorShareClicked(BoardBeanModel boardBeanModel) {

    }
}
