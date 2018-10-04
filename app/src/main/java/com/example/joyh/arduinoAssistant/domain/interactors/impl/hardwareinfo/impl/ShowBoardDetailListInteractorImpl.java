package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowBoardDetailListInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.SharableBeanModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

import static com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel.COLLECTION_TYPE_BOARD;


/**
 * Created by joyn on 2018/9/13 0013.
 */

public class ShowBoardDetailListInteractorImpl extends AbstractInteractor implements ShowBoardDetailListInteractor{

    private BoardRepository boardRepository;
    private Callback callback;
    private BoardBeanModel boardBeanModel;
    private String boardName;
    public ShowBoardDetailListInteractorImpl(String boardName,Executor threadExecutor, MainThread mainThread, BoardRepository boardRepository, ShowBoardDetailListInteractor.Callback callback) {
        super(threadExecutor, mainThread);
        this.boardRepository=boardRepository;
        this.callback=callback;
        this.boardName=boardName;
    }

    @Override
    public void InteractorShowBoardDetailList(String boardName) {

        this.boardName=boardName;
        execute();

    }



    @Override
    public void run() {

        boardBeanModel= boardRepository.getBoardBean(boardName);
        CollectionModel collectionModel=new CollectionModel();
        collectionModel.setName(boardName);
        collectionModel.setType(COLLECTION_TYPE_BOARD);
        callback.onShowBoardDetailList(boardBeanModel);
        callback.onShowBoardCollectionState(boardRepository.getCollectionState(collectionModel));
    }



    @Override
    public void InteractorItemClicked(BoardBeanModel boardBeanModel) {

    }
}
