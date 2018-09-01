package com.example.joyh.arduinoAssistant.presentation.network;

import com.liulishuo.filedownloader.BaseDownloadTask;

/**
 * Created by joyn on 2018/8/25 0025.
 */

public interface FileDownloaderInterface {
    interface Callback{

         void pending(BaseDownloadTask task, int soFarBytes, int totalBytes);
         void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) ;
         void completed(BaseDownloadTask task);
         void paused(BaseDownloadTask task, int soFarBytes, int totalBytes);
         void error(BaseDownloadTask task, Throwable e);
         void warn(BaseDownloadTask task) ;
    }
    void pause( );
    void resume( );

}
