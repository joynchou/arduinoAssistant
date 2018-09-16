package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import android.util.Log;

import com.example.joyh.arduinoAssistant.data.impl.MessageRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.HardwareInfoInteractor;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.domain.repository.MessageRepository;
/**
 * Created by joyn on 2018/8/21 0021.
 * 用例：硬件查询模块主interactor，
 * Interactor : hardware info main interactor
 */

public class HardwareInfoInteractorImpl extends AbstractInteractor implements HardwareInfoInteractor {

    private HardwareInfoInteractor.Callback callback;
    private MessageRepository mMessageRepository;

    private BoardRepository boardRepository;
    private ShowAvailableBoardsInteractorImpl showAvailableBoardsInteractor;


    public HardwareInfoInteractorImpl(Executor threadExecutor,
                                      MainThread mainThread,
                                      BoardRepository boardRepository,
                                      HardwareInfoInteractor.Callback callback,

                                      ShowAvailableBoardsInteractorImpl showAvailableBoardsInteractor
    ) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        //仓库初始化
        mMessageRepository = new MessageRepositoryImpl();
        this.boardRepository = boardRepository;

        this.showAvailableBoardsInteractor=showAvailableBoardsInteractor;


        //model初始化


    }
    @Override
    public void run() {

        showBoards();
    }

    @Override
    public void showBoards() {
        if (boardRepository.getAvailableBoardAmount() != 0) {
            showAvailableBoardsInteractor.execute();
            Log.i("板子数量", "有"+boardRepository.getAvailableBoardAmount()+"个板子");
        } else {
            Log.i("板子数量", "没有板子");
            noBoardAvailabel();

        }

    }

    @Override
    public void noBoardAvailabel() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onNoBoardAvailable();
                //callback.onOpenBoardDownloadManager();
            }
        });
    }
}
