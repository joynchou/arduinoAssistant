package com.example.joyh.arduinoAssistant.domain.interactors.impl.APISearch;

import android.util.Log;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;
import com.example.joyh.arduinoAssistant.data.impl.MessageRepositoryImpl;

import java.util.List;

/**
 * Created by joyn on 2018/8/5 0005.
 */

public class SearchInteractorImpl extends AbstractInteractor implements SearchInteractor {
    private SearchInteractor.Callback mCallback;

    private String string;

    public SearchInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback,
                                MessageRepositoryImpl repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;


        //取得仓库的引用
        MessageRepositoryImpl mRepository = repository;

    }


    private void onNoResult() {
        //
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                //这个是ui里面的函数，在那里被具体实现

            }
        });

        return;
    }


    public void onResults(List<String> result) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    //search搜索功能的实现，来自接口
    @Override
    public void Search(String str) {
        string = str;
        if (!this.isRunning()) {
            this.execute();
        } else {
            Log.i("interactor", "interactor正在运行");
        }

    }
    private void findResultInDB(String data) {
        Log.i("model","model开始执行查询算法");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.onNoResult();
        Log.i("model","model查询结束");
    }
    private void search(String str) {

        this.findResultInDB(string);

        return;

    }

    //此方法在执行execute后会开始运行，是interactor 的主线程
    @Override
    public void run() {
        Log.i("execute", "interactor主线程开始运行");

        this.search(string);
        Log.i("execute", "interactor主线程运行完成");
    }
}
