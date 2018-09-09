package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.DeleteDownloadedBoard;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.DownloadBoardResourceInteractor;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

/**
 * Created by joyn on 2018/9/3 0003.
 */

public class DeleteDownloadedBoardInteractorImpl extends AbstractInteractor implements DeleteDownloadedBoard {


    private BoardRepository boardRepository;
    private DeleteDownloadedBoard.Callback callback;
    private String deleteBoard;

    public DeleteDownloadedBoardInteractorImpl(Executor threadExecutor, MainThread mainThread, BoardRepository boardRepository, Callback callback) {
        super(threadExecutor, mainThread);
        this.boardRepository = boardRepository;
        this.callback = callback;
    }

    @Override
    public void run() {


        boardRepository.deleteBoardResource(deleteBoard);
        callback.onBoardDeleted(deleteBoard);
    }


    @Override
    public void deleteBoard(String name) {
        this.deleteBoard=name;
        execute();

    }
}
