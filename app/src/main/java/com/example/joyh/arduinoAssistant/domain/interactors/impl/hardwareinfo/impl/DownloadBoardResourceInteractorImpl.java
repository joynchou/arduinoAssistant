package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl;

import android.util.Log;

import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.interactors.base.AbstractInteractor;

import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.DownloadBoardResourceInteractor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

import java.util.List;

/**
 * Created by joyn on 2018/8/24 0024.
 * 用例：下载开发板
 * Interactor :download boards
 */

public class DownloadBoardResourceInteractorImpl extends AbstractInteractor implements
        DownloadBoardResourceInteractor
          {

    private DownloadBoardResourceInteractor.Callback callback;

    private String boardname;
    private int position;
    private int downloadState;
    private BoardRepository boardRepository;

    public DownloadBoardResourceInteractorImpl(
            Executor threadExecutor,
            MainThread mainThread,
            BoardRepository boardRepository,
            DownloadBoardResourceInteractor.Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.boardRepository=boardRepository;


    }



    @Override
    public void run() {

        switch (downloadState) {
            //正在下载中点击按钮，则是要暂停下载
            case DOWNLOAD_STATE_DOWNLOADING:
                callback.onBoardDownloadPause(boardname, position);
                break;
            //已经下载好了再点击下载按钮则是要查看
            case DOWNLOAD_STATE_FINISH:
                callback.onBoardDownloadFinish(boardname, position);
                callback.onShowBoardResource(boardname);
                break;
            //在暂停的时候点击。则是要继续下载
            case DOWNLOAD_STATE_PAUSE:
                callback.onBoardDownloadResume(boardname, position);
                break;
            case DOWNLOAD_STATE_RESUME:
                callback.onBoardDownloadPause(boardname, position);
                break;
            case DOWNLOAD_STATE_RETRY:
                callback.onBoardDownloadRetry(boardname, position);
                this.DownloadBoardByName(boardname, position, downloadState);
                break;
            case DOWNLOAD_STATE_UNDOWNLOAD:
                Log.i("interactor", "开始下载");
                callback.onBoardDownloadStarted(boardname, position);
                this.DownloadBoardByName(boardname, position, downloadState);
                break;
        }


    }


    @Override
    public void downloadBoardByName(String name, int listPosition, int downloadState) {
        boardname = "";
        this.position = listPosition;
        this.boardname=name;
        this.downloadState = downloadState;
        this.execute();
    }
    //内部处理下载的方法
    private void DownloadBoardByName(String name, int listPosition, int downloadState){

        Log.i("model", "准备下载： " + name);
        System.out.println("要下载的名称：" + name);
        String boardDetailURL = boardRepository.getBoardDetailURL(name);
        if(boardDetailURL!=null){
            Log.i("boardDetailURL", boardDetailURL);
            List<String> allresource;
            allresource = boardRepository.getAllResource(boardDetailURL);
            Log.i("allresource", allresource.toString());



            for (int i = 0; i < allresource.size(); i++) {
                //TODO:其实这里不应该在view中去实现下载，因为view是处理ui变化的，而下载是内部线程，不是ui
                //暂时没有好的解决方案

                callback.onDownloadResource(allresource.get(i), name, listPosition);
            }
        }
        else{
            Log.e("boardDetailURL", "is null");
        }


    }





    ///////////来自model的接口实现/////////////////////////

    @Override
    public void pending(String name,int taskId, int soFarBytes, int totalBytes) {
        callback.onBoardDownloadStarted(name, taskId);
    }

    @Override
    public void progress(String name,int taskId, int soFarBytes, int totalBytes) {
        callback.onBoardDownloadProgressChange(name, taskId, (soFarBytes * 100) / totalBytes);
    }

    @Override
    public void completed(String name,int taskId) {
        callback.onBoardDownloadFinish(name, taskId);

    }

    @Override
    public void paused(String name,int taskId, int soFarBytes, int totalBytes) {
        callback.onBoardDownloadPause(name, taskId);
    }

    @Override
    public void error(String name,int taskId, Throwable e) {
        callback.onBoardDownloadFailed(name, taskId, e.toString());
    }

    @Override
    public void warn(String name,int taskId) {

    }


}
