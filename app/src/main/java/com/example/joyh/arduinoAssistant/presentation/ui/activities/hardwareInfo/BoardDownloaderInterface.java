package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo;

/**
 * Created by joyn on 2018/8/27 0027.
 */

public interface BoardDownloaderInterface {
    interface Callback {
        void onProgressBarChanged(String boardName, int listPositon, int progress);

        void onBoardDownloadFailed(String boardName, int listPositon);

        void onBoardDownloadFinish(String boardName, int listPositon);

        void onBoardDownloadPause(String boardName, int listPositon);

        void onBoardDownloadResume(String boardName, int listPositon);

        void onBoardDownloadRetry(String boardName, int listPositon);

        void onBoardDownloadStarted(String boardName,int listPosition);

    }

}
