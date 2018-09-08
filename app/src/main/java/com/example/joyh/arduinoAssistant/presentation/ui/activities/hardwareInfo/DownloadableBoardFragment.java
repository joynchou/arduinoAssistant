package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo;


import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.executor.impl.ThreadExecutor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.presentation.network.FileDownloader;
import com.example.joyh.arduinoAssistant.presentation.network.FileDownloaderInterface;
import com.example.joyh.arduinoAssistant.presentation.presenters.BoardDownloaderPresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.impl.BoardDownLoaderPresenterImpl;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;
import com.liulishuo.filedownloader.BaseDownloadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joyn on 2018/9/7 0007.
 * 可下载的板子列表fragment
 */

public class DownloadableBoardFragment extends Fragment implements
        BoardDownloaderPresenter.View,
        DownloadableRecyclerViewAdapterInterface.Callback,
        FileDownloaderInterface.Callback,
        BoardRepository.Callback {

    private BoardDownloaderPresenter mainPresenter;
    private MainThread mainThread;
    private BoardRepositoryImpl boardRepository;
    private Map<String, FileDownloader> downloadMap;
    private Map<Integer, String> taskMap;
    private RecyclerView recyclerView;
    private int sysVersion = Integer.parseInt(Build.VERSION.SDK);
    private DownloadableRecyclerViewAdapter recyclerViewAdapter;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MainThread mainThread = MainThreadImpl.getInstance();
        View view;
        view =  inflater.inflate(R.layout.fragment_downloadable_board, container, false);
        setupRecyclerView(recyclerView);
        recyclerView=view.findViewById(R.id.downloadable_recyclerView);
        return view;
    }

    @Override
    public void onDownloadBoard(final String boardName, final int position, final int downloadState) {

        new Thread() {
            @Override
            public void run() {
                //这里写入子线程需要做的工作
                mainPresenter.downloadBoardByName(boardName, position, downloadState);
            }
        }.start();

    }

   
/////////////////////////////////////////////////

    @Override
    public void showProgress() {

    }

    @Override
    public void onError(String error) {
        
    }

    @Override
    public void onShowDownloadableBoardList(List<BoardBeanModelImpl> boards) {

        List<String> imgURL = new ArrayList<>();
        List<String> boardname = new ArrayList<>();
        List<String> content = new ArrayList<>();
        List<Integer> present = new ArrayList<>();

        imgURL.clear();
        boardname.clear();
        content.clear();
        present.clear();
        downloadMap = new HashMap<>();
        taskMap = new HashMap<>();
        //容器初始化
        for (int i = 0; i < boards.size(); i++) {
            content.add(boards.get(i).getBoardName());
            imgURL.add(boards.get(i).getPicURL());
            boardname.add(boards.get(i).getBoardName());
            present.add(0);
            downloadMap.put(null, null);
            taskMap.put(i, null);
        }

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerViewAdapter = new DownloadableRecyclerViewAdapter(getContext(), imgURL, boardname, content, boardRepository, this);

        recyclerView.setAdapter(recyclerViewAdapter);
        if (sysVersion <= 18) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }
    }
    @Override
    public void hideProgress() {

    }
    @Override
    public void showError(String message) {

    }
    @Override
    public void onProgressBarChanged(String boardName, int listPositon, int progress) {
        if (recyclerViewAdapter != null)
            recyclerViewAdapter.onProgressBarChanged(boardName, listPositon, progress);
    }

    @Override
    public void onBoardDownloadFailed(String boardName, int listPositon, String error) {
        Log.i("download", "failed ");
        if (recyclerViewAdapter != null)
            recyclerViewAdapter.onBoardDownloadFailed(boardName, listPositon);
        showInfo(boardName + "下载失败," + error);
    }

    @Override
    public void onBoardDownloadFinish(String boardName, int listPositon) {
        Log.i("download", "finish");
        if (recyclerViewAdapter != null)
            recyclerViewAdapter.onBoardDownloadFinish(boardName, listPositon);
        showInfo(boardName + "下载完成");
    }

    @Override
    public void onBoardDownloadPause(String boardName, int listPositon) {
        Log.i("download", "pause: ");
        if (recyclerViewAdapter != null)
            recyclerViewAdapter.onBoardDownloadPause(boardName, listPositon);

        FileDownloader fileDownloader;
        fileDownloader = downloadMap.get(listPositon);
        fileDownloader.pause();

    }

    @Override
    public void onBoardDownloadResume(String boardName, int listPositon) {
        Log.i("download", "resume: ");
        if (recyclerViewAdapter != null)
            recyclerViewAdapter.onBoardDownloadResume(boardName, listPositon);

        FileDownloader fileDownloader;
        fileDownloader = downloadMap.get(listPositon);
        fileDownloader.resume();

    }

    @Override
    public void onBoardDownloadRetry(String boardName, int listPositon) {
        if (recyclerViewAdapter != null)
            recyclerViewAdapter.onBoardDownloadRetry(boardName, listPositon);
    }

    @Override
    public void onBoardDownloadStarted(final String boardName, final int listPosition) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                showInfo("开始下载" + boardName);
                // recyclerViewAdapter.onProgressBarChanged(boardName, listPosition, 3);
                recyclerViewAdapter.onBoardDownloadStarted(boardName, listPosition);
            }
        });

    }

    @Override
    public void onDownloadResource(String URL, String name, int position) {

        String path;
        int downloadId;
        String defaultRootPath =
                Environment.getExternalStorageDirectory().getPath();

        FileDownloader fileDownloader = new FileDownloader
                (
                        URL,
                        boardRepository.boardDownloadSavePath(name),
                        getContext(),
                        defaultRootPath,
                        this
                );
        fileDownloader.startTask();
        downloadId = fileDownloader.getTaskId();
        downloadMap.put(name, fileDownloader);
        taskMap.put(downloadId, name);
        Log.i("downloadMap", downloadMap.toString());
    }



    /////////////////////////////////////////////
    //以下方法是filedownload接口的实现，需要转交给presentor，再由presentor转交给domain层/////////////////////////////
    //没有特殊情况不要改动此处代码，也不要在里面加东西
    @Override
    public void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        String name;
        name = taskMap.get(task.getId());
        mainPresenter.pending(name, task.getId(), soFarBytes, totalBytes);
    }

    @Override
    public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        String name;
        name = taskMap.get(task.getId());
        mainPresenter.progress(name, task.getId(), soFarBytes, totalBytes);
        Log.i("fileDownload", "boardName:" + name + "  taskId:" + task.getId() + " bytes:" + soFarBytes);
    }

    @Override
    public void completed(BaseDownloadTask task) {
        String name;
        name = taskMap.get(task.getId());
        mainPresenter.completed(name, task.getId());
    }

    @Override
    public void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        String name;
        name = taskMap.get(task.getId());
        mainPresenter.paused(name, task.getId(), soFarBytes, totalBytes);
    }

    @Override
    public void error(BaseDownloadTask task, Throwable e) {
        String name;
        name = taskMap.get(task.getId());
        mainPresenter.error(name, task.getId(), e);
    }

    @Override
    public void warn(BaseDownloadTask task) {
        String name;
        name = taskMap.get(task.getId());
        mainPresenter.warn(name, task.getId());
    }
    
    private void setupRecyclerView(RecyclerView recyclerView) {


    }

    private void initPresenter() {
        Executor executor = ThreadExecutor.getInstance();
        mainThread = MainThreadImpl.getInstance();
        boardRepository = new BoardRepositoryImpl(getContext(), this);
        //presenter实例化
        mainPresenter = new BoardDownLoaderPresenterImpl(executor, mainThread, boardRepository, this);
        mainPresenter.resume();
    }
    //以下为自定义私有方法
    private void showInfo(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
