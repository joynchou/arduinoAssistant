package com.example.joyh.arduinoAssistant.presentation.presenters.impl;

import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.data.impl.MessageRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.HardwareInfoInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.HardwareInfoInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.HardwareInfoShowAvailableBoardsInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.HardwareInfoShowAvailableBoardsInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.HardwareInfoShowDownloadableBoardsInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
import com.example.joyh.arduinoAssistant.presentation.presenters.HardwareInfoPresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 */

public class HardwareInfoPresenterImpl extends AbstractPresenter implements
        HardwareInfoPresenter,
        HardwareInfoInteractor.Callback,
        HardwareInfoShowAvailableBoardsInteractor.Callback {

    private HardwareInfoPresenter.View view;
    private BoardRepositoryImpl boardRepository;
    private HardwareInfoInteractorImpl infoInteractor;
    HardwareInfoShowDownloadableBoardsInteractorImpl infoShowDownloadableBoardsInteractor;
    private HardwareInfoShowAvailableBoardsInteractorImpl infoShowAvailableBoardsInteractor;
    MessageRepositoryImpl mMessageRepository;

    public HardwareInfoPresenterImpl(Executor executor, MainThread mainThread, BoardRepositoryImpl boardRepository, View view) {
        super(executor, mainThread);
        this.boardRepository=boardRepository;
        this.view = view;

    }


    //在板子浏览界面中显示已下载可用的板子
    @Override
    public void onAvailableBoard(List<BoardBeanModelImpl> boards) {
        view.onShowBoards(boards);
    }

    //显示没有已下载可用的板子的信息
    @Override
    public void onNoBoardAvailable() {
        view.onShowNoAvailableBoard();
    }

    @Override
    public void onOpenBoardDownloadManager() {
        view.onOpenBoardDownloadManager();
    }

    @Override
    public void onShowProgress() {
        view.showProgress();
    }

    @Override
    public void onHideProgress() {
        view.hideProgress();
    }

    @Override
    public void resume() {

        infoShowAvailableBoardsInteractor =
                new HardwareInfoShowAvailableBoardsInteractorImpl
                        (mExecutor, mMainThread, boardRepository,this);

        infoInteractor =
                new HardwareInfoInteractorImpl
                        (       mExecutor,
                                mMainThread,
                                boardRepository,
                                this,
                                infoShowAvailableBoardsInteractor);
        infoInteractor.execute();

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
    public void onShowAvailableBoardsError(String err) {
        view.showError(err);
    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }
}
