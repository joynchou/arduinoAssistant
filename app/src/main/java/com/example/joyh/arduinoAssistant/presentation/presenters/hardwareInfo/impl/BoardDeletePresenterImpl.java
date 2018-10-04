package com.example.joyh.arduinoAssistant.presentation.presenters.hardwareInfo.impl;

import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.DeleteDownloadedBoard;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowDownloadedBoardInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.DeleteDownloadedBoardInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.ShowDownloadedBoardInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.presentation.presenters.hardwareInfo.BoardDeletePresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by joyn on 2018/9/8 0008.
 */

public class BoardDeletePresenterImpl extends AbstractPresenter implements BoardDeletePresenter ,
        ShowDownloadedBoardInteractor.Callback,
        DeleteDownloadedBoard.Callback{

    private ShowDownloadedBoardInteractorImpl downloadedBoardInteractor;
    private DeleteDownloadedBoardInteractorImpl deleteDownloadedBoardInteractor;
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
        deleteDownloadedBoardInteractor=new DeleteDownloadedBoardInteractorImpl(mExecutor,mMainThread,boardRepository,this);
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

    @Override
    public void onBoardDeleted(final String boardName) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                view.onViewDeleteBoard(boardName);
            }
        });

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
    public void onshowDownloadedBoards(List<BoardBeanModel> boards) {
        view.onViewshowDownloadedBoards(boards);
    }

    @Override
    public void deleteBoard(String name) {
        deleteDownloadedBoardInteractor.deleteBoard(name);
    }
}
