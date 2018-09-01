package com.example.joyh.arduinoAssistant.presentation.presenters.impl;

import android.util.Log;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.APISearch.SearchInteractor;

import com.example.joyh.arduinoAssistant.domain.interactors.impl.APISearch.SearchInteractorImpl;
import com.example.joyh.arduinoAssistant.data.impl.MessageRepositoryImpl;
import com.example.joyh.arduinoAssistant.presentation.presenters.APIInfoPresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class APIInfoPresenterImpl extends AbstractPresenter implements APIInfoPresenter,
        SearchInteractor.Callback {

    private APIInfoPresenter.View mView;
    private SearchInteractor searchInteractor;

    public APIInfoPresenterImpl(Executor executor,
                                MainThread mainThread,
                                View view) {

        super(executor, mainThread);
        mView = view;


    }

    @Override
    public void search(String str) {
        mView.showProgress();
        searchInteractor.Search(str);

    }

    @Override
    public void onNoResult() {
        Log.i("result","没有匹配的结果");
        mView.hideProgress();
        mView.showNoResult();
        return;
    }

    @Override
    public void onResults(List<String> result) {
        Log.i("result","找到一些匹配的结果");
        mView.hideProgress();
        mView.showResults(result);

    }
//interactor生命周期
    @Override
    public void resume() {
        //仓库实例化
        MessageRepositoryImpl mMessageRepository = new MessageRepositoryImpl();
        //interactor实例化
        searchInteractor=new SearchInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mMessageRepository
        );

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

    }
}
