package com.example.joyh.arduinoAssistant.domain.interactors.base;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;

/**
 * Created by joyn on 2018/9/5 0005.
 */

public abstract class AbstractCollectionInteractor extends AbstractInteractor {


    public AbstractCollectionInteractor(Executor threadExecutor, MainThread mainThread) {
        super(threadExecutor, mainThread);
    }


    public abstract void run();

    /**
     * 改变可收藏对象的收藏状态
     * @param collectionModel  可收藏对象
     * @param state  收藏状态，true代表收藏，false代表取消收藏
     */
    public abstract void changCollectionState(CollectionModel collectionModel, boolean state);



}
