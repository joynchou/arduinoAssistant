package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import com.example.joyh.arduinoAssistant.data.impl.MessageRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowDownloadableBoardsInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.domain.repository.MessageRepository;

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
        List<BoardBeanModel> beanModels=showDownloadableBoards();
        UIshowDownloadableBoards(beanModels);
        UIhideProgressBar();
    }

    @Override
    public List<BoardBeanModel> showDownloadableBoards() {
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

    private void UIshowDownloadableBoards(final List<BoardBeanModel> boardList) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {

                callback.onShowDownloadableBoards(boardList);

            }
        });
    }






}
