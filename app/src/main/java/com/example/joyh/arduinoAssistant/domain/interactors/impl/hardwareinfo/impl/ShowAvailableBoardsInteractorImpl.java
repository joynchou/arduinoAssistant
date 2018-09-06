package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowAvailableBoardsInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

import static com.example.joyh.arduinoAssistant.domain.repository.BoardRepository.COLLECTION_TYPE_BOARD;

/**
 * Created by joyn on 2018/8/21 0021.
 * 用例：在硬件查询界面展示已经下载的可以用的板子卡片
 * Interactor :show already downloaded board cards;
 */

public class ShowAvailableBoardsInteractorImpl extends AbstractInteractor implements
        ShowAvailableBoardsInteractor {

    private ShowAvailableBoardsInteractor.Callback callback;
    private BoardRepository boardRepository;

    public ShowAvailableBoardsInteractorImpl(Executor threadExecutor,
                                             MainThread mainThread,
                                             BoardRepository boardRepository,
                                             ShowAvailableBoardsInteractor.Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        //仓库初始化

        this.boardRepository = boardRepository;

    }

    @Override
    public void run() {
        if (boardRepository.getAvailableBoardAmount() != 0) {
            UIshowProgressBar();
            showAvailableBoard();
            UIhideProgressBar();
        }


    }

    @Override
    public void showAvailableBoard() {

        UIshowAvailableBoards();

    }
    private void UIshowProgressBar(){
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onShowProgress();
            }
        });
    }
    private void UIhideProgressBar(){
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onHideProgress();
            }
        });
    }

    private void UIshowAvailableBoards() {
        final List<Boolean> collectionState=new ArrayList<>();
        final List<BoardBeanModelImpl> boardList=boardRepository.getAvailableBoards();
        List<String> boardName=new ArrayList<>();
        CollectionModel collectionModel=new CollectionModel();

        for(int i=0;i<boardList.size();i++){
            boardName.add(boardList.get(i).getBoardName());
            collectionModel.setName(boardList.get(i).getBoardName());
            collectionModel.setType(COLLECTION_TYPE_BOARD);
            collectionState.add(boardRepository.getCollectionState(collectionModel));
        }
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onAvailableBoard(boardList,collectionState);
            }
        });

    }


}
