package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowDownloadedBoardInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

import java.util.List;

/**
 * Created by joyn on 2018/9/2 0002.
 * 用例:在下载管理器中展现已经下载好的板子，有别与主页，此界面是用来删除板子的
 * Interactor :Manage already downloaded boards
 */

public class ShowDownloadedBoardInteractorImpl extends AbstractInteractor implements ShowDownloadedBoardInteractor {

    private ShowDownloadedBoardInteractor.Callback callback;
    private BoardRepository boardRepository;

    public ShowDownloadedBoardInteractorImpl(Executor threadExecutor, MainThread mainThread, BoardRepository boardRepository ,ShowDownloadedBoardInteractor.Callback callback) {
        super(threadExecutor, mainThread);
        this.callback=callback;
        this.boardRepository=boardRepository;

    }

    @Override
    public void run() {
        showDownloadedBoards();
    }


    @Override
    public void deletaBoard(String boardName) {

    }

    @Override
    public void showDownloadedBoards() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onshowDownloadedBoards(boardRepository.getAvailableBoards());
            }
        });


    }

    @Override
    public void refreshDownloadedList() {

    }
}
