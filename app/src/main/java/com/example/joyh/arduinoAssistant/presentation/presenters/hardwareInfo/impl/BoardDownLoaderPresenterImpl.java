package com.example.joyh.arduinoAssistant.presentation.presenters.hardwareInfo.impl;

import android.util.Log;

import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.DownloadBoardResourceInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.DownloadBoardResourceInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.ShowDownloadableBoardsInteractor;
import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.ShowDownloadableBoardsInteractorImpl;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.presentation.presenters.hardwareInfo.BoardDownloaderPresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by joyn on 2018/8/23 0023.
 */

public class BoardDownLoaderPresenterImpl extends AbstractPresenter implements BoardDownloaderPresenter,
        ShowDownloadableBoardsInteractor.Callback ,
        DownloadBoardResourceInteractor.Callback
{
    private BoardDownloaderPresenter.View view;
    private ShowDownloadableBoardsInteractorImpl infoShowDownloadableBoardsInteractor;
    private DownloadBoardResourceInteractorImpl downloadBoardResourceInteractor;
    private BoardRepositoryImpl boardRepository;
    public BoardDownLoaderPresenterImpl(Executor threadExecutor, MainThread mainThread, BoardRepositoryImpl boardRepository, View view) {
        super(threadExecutor, mainThread);
        this.view = view;
        this.boardRepository=boardRepository;
    }

    @Override
    public void downloadBoardByName(String name,int listPosition,int downloadState) {
        downloadBoardResourceInteractor.downloadBoardByName(name,listPosition,downloadState);
    }


    @Override
    public void refresh() {

        if(!infoShowDownloadableBoardsInteractor.isRunning()) {
            infoShowDownloadableBoardsInteractor.execute();
        }
    }

    @Override
    public void resume() {
        infoShowDownloadableBoardsInteractor=
                new ShowDownloadableBoardsInteractorImpl(
                        mExecutor,
                        mMainThread,
                        boardRepository,
                        this);
        infoShowDownloadableBoardsInteractor.execute();

        downloadBoardResourceInteractor=
                new DownloadBoardResourceInteractorImpl(mExecutor,mMainThread,boardRepository,this);

        //不用execute()
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



    //在下载管理器中没有可下载的板子
    @Override
    public void onNoDownloadableBoard() {

    }
    @Override
    public void onShowProgress() {
        view.showProgress();

    }

    @Override
    public void onHideProgress() {
        view.hideProgress();
    }

    @Override
    public void onShowDownLoadableBoardsError(String err) {
        view.showError(err);
    }

    @Override
    public void onDownloadResource(String URL, String name, int position) {
        view.onDownloadResource(URL, name, position);
    }

    @Override
    public void onDownloadBoardResourceInteractorError(String error) {
        view.showError(error);
    }

    //在板子下载器中显示可以下载的板子
    @Override
    public void onShowDownloadableBoards(List<BoardBeanModel> boards) {

        Log.i("list大小", "onShowDownloadableBoards: " + boards.size());
        Log.i("可下载的板子", "onShowDownloadableBoards: " + boards.toString());
        view.onShowDownloadableBoardList(boards);
    }

    @Override
    public void onShowBoardResource(String boardName) {
        view.onViewShowBoardResource(boardName);
    }

    ////////////////////////////////////////////////////////
    @Override
    public void onBoardDownloadProgressChange(String boardName, int listPositon, int progress) {
        view.onProgressBarChanged(boardName, listPositon, progress);
    }

    @Override
    public void onBoardDownloadFailed(String boardName, int listPositon,String error) {
        view.onBoardDownloadFailed(boardName, listPositon,error);
    }

    @Override
    public void onBoardDownloadFinish(String boardName, int listPositon) {
        view.onBoardDownloadFinish(boardName, listPositon);
    }

    @Override
    public void onBoardDownloadPause(String boardName, int listPositon) {
        view.onBoardDownloadPause(boardName, listPositon);
    }

    @Override
    public void onBoardDownloadResume(String boardName, int listPositon) {
        view.onBoardDownloadResume(boardName,listPositon);
    }

    @Override
    public void onBoardDownloadRetry(String boardName, int listPositon) {
        view.onBoardDownloadRetry(boardName,listPositon);
    }

    @Override
    public void onBoardDownloadStarted(String boardName, int listPosition) {
        view.onBoardDownloadStarted(boardName,listPosition);
    }

    /////////////////////////////////////////////


    @Override
    public void pending(String name,int taskId, int soFarBytes, int totalBytes) {

        downloadBoardResourceInteractor.pending(name,taskId,soFarBytes,totalBytes);
    }

    @Override
    public void progress(String name,int taskId, int soFarBytes, int totalBytes) {

        downloadBoardResourceInteractor.progress(name,taskId,soFarBytes,totalBytes);
    }

    @Override
    public void completed(String name,int taskId) {

        downloadBoardResourceInteractor.completed(name,taskId);
    }

    @Override
    public void paused(String name,int taskId, int soFarBytes, int totalBytes) {

        downloadBoardResourceInteractor.paused(name,taskId,soFarBytes,totalBytes);
    }

    @Override
    public void error(String name,int taskId, Throwable e) {


        downloadBoardResourceInteractor.error(name,taskId,e );
    }

    @Override
    public void warn(String name,int taskId) {


        downloadBoardResourceInteractor.warn(name,taskId );
    }
}
