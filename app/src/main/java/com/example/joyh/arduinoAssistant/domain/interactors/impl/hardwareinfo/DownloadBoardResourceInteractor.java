package com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo;


import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;


/**
 * Created by joyn on 2018/8/24 0024.
 */

public interface DownloadBoardResourceInteractor extends Interactor {
    //下载状态
    public static final int DOWNLOAD_STATE_UNDOWNLOAD = 0;//没有下载
    public static final int DOWNLOAD_STATE_DOWNLOADING = 1;//正在下载
    public static final int DOWNLOAD_STATE_PAUSE = 2;//下载暂停
    public static final int DOWNLOAD_STATE_FINISH = 3;//下载完成
    public static final int DOWNLOAD_STATE_RESUME = 4;//下载继续
    public static final int DOWNLOAD_STATE_RETRY = 5;//失败重试
    interface Callback{
        void onBoardDownloadProgressChange(String boardName,int listPositon,int progress);
        void onBoardDownloadFailed(String boardName,int listPositon,String error);
        void onBoardDownloadFinish(String boardName,int listPositon);
        void onBoardDownloadPause(String boardName,int listPositon);
        void onBoardDownloadResume(String boardName,int listPositon);
        void onBoardDownloadRetry(String boardName,int listPositon);
        void onBoardDownloadStarted(String boardName,int listPosition);
        //在presentition层实现，presentition层再让其他层实现
        void onDownloadResource(String URL,String name,int position);
        void onDownloadBoardResourceInteractorError(String error);
    }
    void downloadBoardByName(String name,int position,int downloadState);


    void pending(String name,int taskId, int soFarBytes, int totalBytes);

    void progress(String name,int taskId, int soFarBytes, int totalBytes);

    void completed(String name,int taskId);

    void paused(String name,int taskId, int soFarBytes, int totalBytes);

    void error(String name,int taskId, Throwable e);

    void warn(String name,int taskId);

}
