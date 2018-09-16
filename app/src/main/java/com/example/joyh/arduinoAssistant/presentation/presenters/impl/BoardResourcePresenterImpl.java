package com.example.joyh.arduinoAssistant.presentation.presenters.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowBoardDetailListInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowBoardResourceInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.ShowBoardDetailListInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.ShowBoardResourceInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.SharableBeanModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.presentation.presenters.BoardResourcePresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.AbstractPresenter;

/**
 * Created by joyn on 2018/9/14 0014.
 */

public class BoardResourcePresenterImpl extends AbstractPresenter implements BoardResourcePresenter,

        ShowBoardResourceInteractor.Callback,
        ShowBoardDetailListInteractor.Callback
{
    private BoardRepository boardRepository;
    BoardResourcePresenter.View view;
    private ShowBoardResourceInteractor boardResourceInteractor;
    private ShowBoardDetailListInteractor boardDetailListInteractor;
    private String boardName;
    public BoardResourcePresenterImpl(String boardName,Executor executor, MainThread mainThread, BoardRepository boardRepository, BoardResourcePresenter.View view) {
        super(executor, mainThread);
        this.view=view;
        this.boardRepository=boardRepository;
        this.boardName=boardName;
    }

    @Override
    public void resume() {

    boardDetailListInteractor=new ShowBoardDetailListInteractorImpl(boardName,mExecutor,mMainThread,boardRepository,this);
    boardResourceInteractor =new ShowBoardResourceInteractorImpl(mExecutor,mMainThread,boardRepository,this);
    boardDetailListInteractor.execute();
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
        view.showError(message);
    }

    @Override
    public void onShowBoardResource(String boardName, int resourceType) {
        boardResourceInteractor.InteractorShowBoardResource(boardName,resourceType);
    }

    @Override
    public void onShowBoardResourceError(String error) {
        view.showError(error);
    }

    @Override
    public void onShareResource(SharableBeanModel sharable) {
        view.onViewShowResource(sharable);
    }

    @Override
    public void onShowBoardDetailError(String error) {
        view.showError(error);
    }

    @Override
    public void onShowBoardDetailList(BoardBeanModel detailList) {
        view.onViewShowDetailList(detailList);
    }

    @Override
    public void onShowBoardCollectionState(boolean state) {
        view.onViewShowBoardCollectionState(state);
    }
}
