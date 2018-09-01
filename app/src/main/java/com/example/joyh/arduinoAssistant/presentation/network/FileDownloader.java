package com.example.joyh.arduinoAssistant.presentation.network;

import android.content.Context;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.util.FileDownloadUtils;


/**
 * Created by joyn on 2018/8/25 0025.
 * 集成的下载框架
 * 一个下载对象对应一个下载任务
 * 如果需要使用下载服务需要实现
 * 下载器的接口
 * 作为下载器的回调函数
 */

public class FileDownloader implements FileDownloaderInterface {
    private String URL;
    private String path;
    private  FileDownloaderInterface.Callback callback;
    private Context context;
    private int taskId;
    private int refreshTime;
    private String defaultRootPath;
    private BaseDownloadTask task;


    public FileDownloader(String URL, String path,Context context,String defaultRootPath, final FileDownloaderInterface.Callback callback) {
        Log.i("FileDownloader", "into FileDownloader ");
        this.URL = URL;
        this.context = context;
        this.path=path;
        com.liulishuo.filedownloader.FileDownloader.setup(context);
        this.defaultRootPath=defaultRootPath;
        FileDownloadUtils.setDefaultSaveRootPath(defaultRootPath);
        this.refreshTime=100;
        this.callback=callback;
        com.liulishuo.filedownloader.FileDownloader.setup(context);
    }
    public FileDownloader(String URL, String path,int refreshTime,Context context,String defaultRootPath, final FileDownloaderInterface.Callback callback) {
        Log.i("FileDownloader", "into FileDownloader ");
        this.URL = URL;
        this.path= path;
        this.context = context;
        FileDownloadUtils.setDefaultSaveRootPath(defaultRootPath);
        this.refreshTime=refreshTime;
        this.defaultRootPath=defaultRootPath;
        this.callback=callback;
        com.liulishuo.filedownloader.FileDownloader.setup(context);

    }
    public void startTask(){
        this.task=com.liulishuo.filedownloader.FileDownloader.getImpl().create(URL);
        if(task!=null) {


            this.taskId = task
                    .setPath(path, false)
                    .setCallbackProgressTimes(refreshTime)
                    .setListener(new DownloadListener())
                    .start();
        }
        else {//错误

        }

    }

    public int getDownloadSpeed() {
        int downloadSpeed = task.getSpeed();
        return downloadSpeed;
    }


    public int getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
    }


    @Override
    public void pause() {
        this.task.pause();
    }

    @Override
    public void resume( ) {
        this.startTask();
    }

    class DownloadListener extends FileDownloadListener{
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            callback.pending(task, soFarBytes, totalBytes);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            callback.progress(task, soFarBytes, totalBytes);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            callback.completed(task);
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            callback.paused(task, soFarBytes, totalBytes);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            callback.error(task, e);
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            callback.warn(task);
        }
    }
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public String getDefaultRootPath() {
        return defaultRootPath;
    }

    public void setDefaultRootPath(String defaultRootPath) {
        this.defaultRootPath = defaultRootPath;
        FileDownloadUtils.setDefaultSaveRootPath(defaultRootPath);
    }

    public BaseDownloadTask getTask() {
        return task;
    }

    public void setTask(BaseDownloadTask task) {
        this.task = task;
    }
    @Override
    public String toString() {
        return "FileDownloader{" +
                "URL='" + URL + '\'' +
                ", path='" + path + '\'' +
                ", context=" + context +
                ", taskId=" + taskId +
                '}';
    }




}
