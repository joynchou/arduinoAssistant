package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;

import com.example.joyh.arduinoAssistant.data.impl.MessageRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.domain.repository.MessageRepository;

/**
 * Created by joyn on 2018/8/21 0021.
 * 用例：在硬件查询界面展示已经下载的可以用的板子卡片
 */

public class HardwareInfoShowAvailableBoardsInteractorImpl extends AbstractInteractor implements
        HardwareInfoShowAvailableBoardsInteractor {

    private HardwareInfoShowAvailableBoardsInteractor.Callback callback;
    private MessageRepository mMessageRepository;
    private BoardRepository boardRepository;

    public HardwareInfoShowAvailableBoardsInteractorImpl(Executor threadExecutor,
                                                         MainThread mainThread,
                                                         BoardRepository boardRepository,
                                                         HardwareInfoShowAvailableBoardsInteractor.Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        //仓库初始化
        mMessageRepository = new MessageRepositoryImpl();
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
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onAvailableBoard(boardRepository.getAvailableBoards());
            }
        });

    }


}
