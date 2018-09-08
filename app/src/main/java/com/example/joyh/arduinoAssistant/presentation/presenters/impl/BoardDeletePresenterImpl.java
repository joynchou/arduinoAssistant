package com.example.joyh.arduinoAssistant.presentation.presenters.impl;

import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowDownloadedBoardInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.ShowDownloadedBoardInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
import com.example.joyh.arduinoAssistant.presentation.presenters.BoardDeletePresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by joyn on 2018/9/8 0008.
 */

public class BoardDeletePresenterImpl extends AbstractPresenter implements BoardDeletePresenter ,
        ShowDownloadedBoardInteractor.Callback{

    private ShowDownloadedBoardInteractorImpl downloadedBoardInteractor;
    private BoardRepositoryImpl boardRepository;
    private BoardDeletePresenter.View view;

    public BoardDeletePresenterImpl(Executor executor, MainThread mainThread, BoardRepositoryImpl boardRepository, View view) {
        super(executor, mainThread);
        this.boardRepository = boardRepository;
        this.view = view;
    }

    public void resume() {

        downloadedBoardInteractor=new ShowDownloadedBoardInteractorImpl(mExecutor,mMainThread,boardRepository,this);
        downloadedBoardInteractor.execute();
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

    //////////////////////////////////////////////////////////
    @Override
    public void onDeleteBoard(String boardName) {
        view.onViewDeleteBoard(boardName);
    }

    @Override
    public void onNoDownloadedBoard() {
        view.onViewNoDownloadedBoard();
    }

    @Override
    public void onshowDownloadedBoards(List<BoardBeanModelImpl> boards) {
        view.onViewshowDownloadedBoards(boards);
    }

    @Override
    public void deleteBoard(String name) {

    }
}
