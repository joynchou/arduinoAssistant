package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowBoardResourceInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.SharableBeanModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

/**
 * Created by joyn on 2018/9/14 0014.
 */

public class ShowBoardResourceInteractorImpl extends AbstractInteractor implements ShowBoardResourceInteractor
{
    private BoardRepository boardRepository;
    private ShowBoardResourceInteractor.Callback callback;
    public ShowBoardResourceInteractorImpl(Executor threadExecutor, MainThread mainThread,BoardRepository boardRepository,Callback callback) {
        super(threadExecutor, mainThread);
        this.boardRepository=boardRepository;
        this.callback=callback;
    }

    @Override
    public void InteractorShareResource(SharableBeanModel sharable) {

    }

    @Override
    public void InteractorShowBoardResource(String boardname, int resourceType) {

    }

    @Override
    public void run() {

    }
}
