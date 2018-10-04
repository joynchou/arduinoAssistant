package com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.CollectionListInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

import static com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel.COLLECTION_TYPE_BOARD;

/**
 * Created by joyn on 2018/9/17 0017.
 */

public class CollectionListInteractorImpl extends AbstractInteractor implements CollectionListInteractor {
    private BoardRepository boardRepository;
    private CollectionListInteractor.Callback callback;
    public CollectionListInteractorImpl(Executor threadExecutor, MainThread mainThread,BoardRepository boardRepository,Callback callback) {
        super(threadExecutor, mainThread);
        this.boardRepository=boardRepository;
        this.callback=callback;
    }

    @Override
    public void run() {

        showCollectionList();
    }

    @Override
    public void showCollectionList() {

        List<CollectionModel> collectionModelList=new ArrayList<>();
        collectionModelList= boardRepository.getCollectionStateList(COLLECTION_TYPE_BOARD);
        callback.onShowCollectionList(collectionModelList);


    }

    @Override
    public void openColletionItem() {

    }
}
