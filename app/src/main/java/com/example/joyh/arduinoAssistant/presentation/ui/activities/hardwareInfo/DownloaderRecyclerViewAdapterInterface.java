package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo;

/**
 * Created by joyn on 2018/8/24 0024.
 */

interface DownloaderRecyclerViewAdapterInterface {
    interface Callback{
        void onDownloadBoard(String boardName, int position,int downloadState);

    }
    BoardDownloaderInterface.Callback getCallback();
}
