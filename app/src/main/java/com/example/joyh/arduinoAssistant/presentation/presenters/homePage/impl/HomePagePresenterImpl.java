package com.example.joyh.arduinoAssistant.presentation.presenters.homePage.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.CollectionListInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.ItemDetailInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.impl.CollectionListInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage.impl.ItemDetailInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.AbstractPresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.homePage.HomePagePresenter;

import java.util.List;


/**
 * Created by joyn on 2018/9/17 0017.
 */

public class HomePagePresenterImpl extends AbstractPresenter implements HomePagePresenter ,
CollectionListInteractor.Callback,
ItemDetailInteractor.Callback{
private HomePagePresenter.View view;
private  CollectionListInteractor collectionListInteractor;
private ItemDetailInteractor itemDetailInteractor;
    public HomePagePresenterImpl(Executor executor, MainThread mainThread, BoardRepository boardRepository,View view) {
        super(executor, mainThread);
        this.view=view;
        collectionListInteractor=new CollectionListInteractorImpl(mExecutor,mMainThread,boardRepository,this);
        itemDetailInteractor=new ItemDetailInteractorImpl(mExecutor,mMainThread,this);

    }

    @Override
    public void resume() {
        collectionListInteractor.execute();
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
    public void onError(String message) {
        view.showError(message);
    }

    @Override
    public void onShowCollectionList(final List<CollectionModel> collectionList) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                view.onViewShowCollectionList(collectionList);
            }
        });

    }

    @Override
    public void onOpenColletionItem(CollectionModel collectionItem) {
        view.onViewOpenColletionItem(collectionItem);
    }

    @Override
    public void presenterItemClicked(CollectionModel collectionModel) {
        itemDetailInteractor.interactorItemClicked(collectionModel);
    }

    @Override
    public void onShowItem(CollectionModel collectionModel) {
        view.onViewOpenColletionItem(collectionModel);
    }
}
