package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractCollectionInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.BoardCollectionInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

import static com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel.COLLECTION_TYPE_BOARD;

/**
 * Created by joyn on 2018/9/3 0003.
 * 用例：收藏已下载的板子
 * Interactor :collect already downloaded board
 */

public class BoardCollectionInteractorImpl extends AbstractCollectionInteractor implements BoardCollectionInteractor {


    private BoardCollectionInteractor.Callback callback;
    private BoardRepository boardRepository;
    private CollectionModel collectionModel;
    private boolean state;

    public BoardCollectionInteractorImpl(Executor threadExecutor, MainThread mainThread, BoardRepository boardRepository, Callback callback) {
        super(threadExecutor, mainThread);
        this.boardRepository = boardRepository;
        this.callback = callback;
    }

    //外部调用execute方法的时候就是需要收藏板子的时候
    @Override
    public void run() {

        if (state) {
            this.setStarButtonClicked();
        } else {
            this.setStarButtonUnClicked();
        }
        this.boardRepository.changeCollectionState(collectionModel, state);
    }

    @Override
    public void usecaseChangCollectionState(CollectionModel collectionModel, boolean state) {
        this.changCollectionState(collectionModel, state);

    }


    @Override
    public void changCollectionState(CollectionModel collectionModel, boolean state) {
        this.collectionModel = collectionModel;
        this.state = state;
        collectionModel.setState(state);
        this.execute();
    }

    /**
     * ui更新,
     */
    private void setStarButtonClicked() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCollectionStateChanged(collectionModel, true);
            }
        });

    }

    private void setStarButtonUnClicked() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCollectionStateChanged(collectionModel, false);
            }
        });
    }

    @Override
    public void usecaseStarButtonClicked(BoardBeanModel board) {

        CollectionModel collectionModel = new CollectionModel();
        collectionModel.setName(board.getBoardName());
        collectionModel.setType(COLLECTION_TYPE_BOARD);
        collectionModel.setCollectionBean(board);

        boolean state = boardRepository.getCollectionState(collectionModel);
        boardRepository.changeCollectionState(collectionModel,!state);
        this.usecaseChangCollectionState(collectionModel, !state);


    }
}
