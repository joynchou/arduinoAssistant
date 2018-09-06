package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import com.example.joyh.arduinoAssistant.data.impl.MessageRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowDownloadableBoardsInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.domain.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 * 用例：在下载管理器中展示可以供下载的板子列表
 * Interactor :show downloadable boards in download manager list
 */

public class ShowDownloadableBoardsInteractorImpl extends AbstractInteractor implements
        ShowDownloadableBoardsInteractor {
    private ShowDownloadableBoardsInteractor.Callback callback;
    private MessageRepository mMessageRepository;

    private BoardRepository boardRepository;

    public ShowDownloadableBoardsInteractorImpl(Executor threadExecutor,
                                                MainThread mainThread,
                                                BoardRepository boardRepository,
                                                ShowDownloadableBoardsInteractor.Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        //仓库初始化
        mMessageRepository = new MessageRepositoryImpl();
        this.boardRepository = boardRepository;
        //model初始化


    }

    @Override
    public void run() {

        refreshDownloadList();


    }

    @Override
    public void refreshDownloadList() {
        UIshowProgressBar();
        List<BoardBeanModelImpl> beanModels=showDownloadableBoards();
        UIshowDownloadableBoards(beanModels);
        UIhideProgressBar();
    }

    @Override
    public List<BoardBeanModelImpl> showDownloadableBoards() {
//        List<BoardBeanModelImpl> boardList = new ArrayList<>();
//        List<String> boardName;
//        List<String> boardURL;
//
//        boardName = boardRepository.getDownloadableBoardName(BoardRepository.ENTRY_LEVEL);
//        boardURL = boardRepository.getDownloadableBoardImgURL(BoardRepository.ENTRY_LEVEL);
//        boardName.addAll(boardRepository.getDownloadableBoardName(BoardRepository.ENHANCED_FEATURES));
//        boardURL.addAll(boardRepository.getDownloadableBoardImgURL(BoardRepository.ENHANCED_FEATURES));
//        boardName.addAll(boardRepository.getDownloadableBoardName(BoardRepository.RETIRED));
//        boardURL.addAll(boardRepository.getDownloadableBoardImgURL(BoardRepository.RETIRED));
//        for (int i = 0; i < boardName.size(); i++) {
//            BoardBeanModelImpl board = new BoardBeanModelImpl();
//            board.setBoardName(boardName.get(i));
//            board.setPicURL(boardURL.get(i));
//            boardList.add(board);
//
//        }
        return boardRepository.getDownloadableBoards();
    }

    private void UIshowProgressBar() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onShowProgress();
            }
        });
    }

    private void UIhideProgressBar() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onHideProgress();
            }
        });
    }

    private void UIshowDownloadableBoards(final List<BoardBeanModelImpl> boardList) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {

                callback.onShowDownloadableBoards(boardList);

            }
        });
    }

    void UIshowError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }




}
