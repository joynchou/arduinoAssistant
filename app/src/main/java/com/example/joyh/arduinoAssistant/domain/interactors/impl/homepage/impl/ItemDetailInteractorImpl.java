package com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.ItemDetailInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;

/**
 * Created by joyn on 2018/9/17 0017.
 */

public class ItemDetailInteractorImpl extends AbstractInteractor implements ItemDetailInteractor {
    private ItemDetailInteractor.Callback callback;
    private CollectionModel collectionModel;

    public ItemDetailInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.onShowItem(collectionModel);
    }

    @Override
    public void interactorItemClicked(CollectionModel collectionModel) {
        this.collectionModel = collectionModel;
        execute();


    }
}
