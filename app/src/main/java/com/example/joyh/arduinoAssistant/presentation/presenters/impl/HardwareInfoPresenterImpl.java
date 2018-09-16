package com.example.joyh.arduinoAssistant.presentation.presenters.impl;

import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.BoardCollectionInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.HardwareInfoInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowAvailableBoardsInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowBoardDetailListInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.BoardCollectionInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.HardwareInfoInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.ShowAvailableBoardsInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.ShowBoardDetailListInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.presentation.presenters.HardwareInfoPresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 */

public class HardwareInfoPresenterImpl extends AbstractPresenter implements
        HardwareInfoPresenter,
        HardwareInfoInteractor.Callback,
        ShowAvailableBoardsInteractor.Callback,
        BoardCollectionInteractor.Callback {

    private HardwareInfoPresenter.View view;
    private BoardRepositoryImpl boardRepository;
    private HardwareInfoInteractorImpl infoInteractor;
    private ShowAvailableBoardsInteractorImpl infoShowAvailableBoardsInteractor;
    private BoardCollectionInteractorImpl collectionInteractor;


    public HardwareInfoPresenterImpl(
            Executor executor,
            MainThread mainThread,
            BoardRepositoryImpl boardRepository,
            View view
    ) {
        super(executor, mainThread);
        this.boardRepository = boardRepository;
        this.view = view;

    }

    @Override
    public void presenterCollectBoard(String boardName) {

    }

    @Override
    public void onError(String error) {
        view.showError(error);
    }

    @Override
    public void onCollectionStateChanged(CollectionModel model, boolean state) {
        view.onViewChangeCollectionState(model.getName(), state);
    }




    //在板子浏览界面中显示已下载可用的板子
    @Override
    public void onAvailableBoard(List<BoardBeanModel> boards, List<Boolean> collectionState) {
        view.onShowBoards(boards, collectionState);
    }

    //显示没有已下载可用的板子的信息
    @Override
    public void onNoBoardAvailable() {
        view.onShowNoAvailableBoard();
    }

    @Override
    public void presenterStarButtonClicked(String boardName) {
        collectionInteractor.usecaseStarButtonClicked(boardName);
    }

    @Override
    public void presenterCardClicked(String boardName) {
        BoardBeanModel boardBeanModel=new BoardBeanModel();
        boardBeanModel.setBoardName(boardName);


        infoShowAvailableBoardsInteractor.InteractorOpenBoardDetailList(boardBeanModel);
    }

    @Override
    public void onOpenBoardDetailList(BoardBeanModel board) {
        view.onViewShowBoardDetailList(board);
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
                new ShowAvailableBoardsInteractorImpl
                        (mExecutor, mMainThread, boardRepository, this);

        infoInteractor =
                new HardwareInfoInteractorImpl
                        (
                                mExecutor,
                                mMainThread,
                                boardRepository,
                                this,
                                infoShowAvailableBoardsInteractor
                        );
        infoInteractor.execute();
        collectionInteractor = new BoardCollectionInteractorImpl
                (
                        mExecutor,
                        mMainThread,
                        boardRepository,
                        this
                );


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


}
