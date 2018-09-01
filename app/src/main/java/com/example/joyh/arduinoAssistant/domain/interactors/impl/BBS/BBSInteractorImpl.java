package com.example.joyh.arduinoAssistant.domain.interactors.impl.BBS;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;

/**
 * Created by joyn on 2018/8/6 0006.
 */

public class BBSInteractorImpl extends AbstractInteractor implements BBSInteractor {

    public BBSInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback ) {
        super(threadExecutor, mainThread);
        //mCallback = callback;
        //model实例化
        //model = new BBSInteractorImpl();
        //取得仓库的引用


    }
    @Override
    public void run() {

    }

    @Override
    public void openURL(String url) {

    }

    @Override
    public void forward() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void refresh() {

    }
}
