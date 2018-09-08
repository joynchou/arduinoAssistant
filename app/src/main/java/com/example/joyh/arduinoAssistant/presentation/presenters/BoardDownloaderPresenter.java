package com.example.joyh.arduinoAssistant.presentation.presenters;

import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.BasePresenter;
import com.example.joyh.arduinoAssistant.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by joyn on 2018/8/23 0023.
 */

public interface BoardDownloaderPresenter extends BasePresenter {
    interface View extends BaseView {
        void onShowDownloadableBoardList(List<BoardBeanModelImpl> boards);

        void onProgressBarChanged(String boardName, int listPositon, int progress);

        void onBoardDownloadFailed(String boardName, int listPositon, String error);

        void onBoardDownloadFinish(String boardName, int listPositon);

        void onBoardDownloadPause(String boardName, int listPositon);

        void onBoardDownloadResume(String boardName, int listPositon);

        void onBoardDownloadRetry(String boardName, int listPositon);

        void onDownloadResource(String URL, String name, int position);

        void onBoardDownloadStarted(String boardName, int listPosition);


    }

    void refresh();

    void downloadBoardByName(String name, int position,int downloadState);

    void pending(String name,int taskId, int soFarBytes, int totalBytes);

    void progress(String name,int taskId, int soFarBytes, int totalBytes);

    void completed(String name,int taskId);

    void paused(String name,int taskId, int soFarBytes, int totalBytes);

    void error(String name,int taskId, Throwable e);

    void warn(String name,int taskId);

}
