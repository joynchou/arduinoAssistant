package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aitangba.swipeback.SwipeBackActivity;
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
import com.example.joyh.arduinoAssistant.presentation.ui.activities.apiinfo.TabFragmentPagerAdapter;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;
import com.githang.statusbar.StatusBarCompat;
import com.liulishuo.filedownloader.BaseDownloadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joyn on 2018/8/22 0022.
 */

public class BoardDownloader extends SwipeBackActivity implements BoardDownloaderPresenter.View,
        AdapterInterface.Callback,
        FileDownloaderInterface.Callback,
        BoardRepository.Callback {
    private BoardDownloaderPresenter mainPresenter;
    private MainThread mainThread;
    private ProgressBar progressBar;
    private TextView info;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private BoardDownloaderInterface.Callback callback;
    private BaseDownloadTask downloadTask;
    private Map<String, FileDownloader> downloadMap;
    private Map<Integer, String> taskMap;
    private DownloaderRecyclerViewAdapter adapter;
    private BoardRepositoryImpl boardRepository;
    int sysVersion = Integer.parseInt(Build.VERSION.SDK);

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_board_downloader);

        initToorbor();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setNestedScrollingEnabled(false);


        initPresenter();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_downloader, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_refresh:


                mainPresenter.refresh();
                adapter.notifyDataSetChanged();


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> imgURL = new ArrayList<>();
    private List<String> boardname = new ArrayList<>();
    private List<String> content = new ArrayList<>();
    private List<Integer> present = new ArrayList<>();


    @Override
    public void onShowDownloadableBoardList(List<BoardBeanModelImpl> boards) {

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
        adapter = new DownloaderRecyclerViewAdapter(this, imgURL, boardname, content, boardRepository, this);
        callback = adapter.getCallback();
        recyclerView.setAdapter(adapter);
        if (sysVersion <= 18) {
            recyclerView.addItemDecoration(new DividerItemDecoration(BoardDownloader.this, DividerItemDecoration.VERTICAL));
        }

    }


    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
        Log.i("progressbar", " is opened");
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        Log.i("progressbar", " is hide");
    }

    @Override
    public void showError(String message) {
        showInfo(message);
    }

    //adapter的回调函数实现
    @Override
    public void onDownloadBoard(final String boardName, final int listPosition, final int downloadState) {
        new Thread() {
            @Override
            public void run() {
                //这里写入子线程需要做的工作
                mainPresenter.downloadBoardByName(boardName, listPosition, downloadState);
            }
        }.start();


    }


    //以下为自定义私有方法
    private void showInfo(String message) {
        Toast.makeText(BoardDownloader.this, message, Toast.LENGTH_SHORT).show();
    }

    private void initToorbor() {

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), false);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("下载器");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void initPresenter() {
        Executor executor = ThreadExecutor.getInstance();
        mainThread = MainThreadImpl.getInstance();
        boardRepository = new BoardRepositoryImpl(getApplicationContext(), this);
        //presenter实例化
        mainPresenter = new BoardDownLoaderPresenterImpl(executor, mainThread, boardRepository, this);
        mainPresenter.resume();
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();

        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ArduinoBoardFragment(), "Arduino");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDownloadResource(String URL, String name, final int position) {
        String path;
        int downloadId;
        String defaultRootPath =
                Environment.getExternalStorageDirectory().getPath();

        FileDownloader fileDownloader = new FileDownloader
                (
                        URL,
                        boardRepository.boardDownloadSavePath(name),
                        this,
                        defaultRootPath,
                        this
                );
        fileDownloader.startTask();
        downloadId = fileDownloader.getTaskId();
        downloadMap.put(name, fileDownloader);
        taskMap.put(downloadId, name);
        Log.i("downloadMap", downloadMap.toString());


    }

    @Override
    public void onError(String error) {
        showError(error);
    }

    ////////////此处的接口实现来自presenter////////////////////////
    @Override
    public void onProgressBarChanged(String boardName, int listPositon, int progress) {
        if (callback != null)
            callback.onProgressBarChanged(boardName, listPositon, progress);
    }

    @Override
    public void onBoardDownloadFailed(String boardName, int listPositon, String error) {
        Log.i("download", "failed ");
        if (callback != null)
            callback.onBoardDownloadFailed(boardName, listPositon);
        showInfo(boardName + "下载失败," + error);
    }

    @Override
    public void onBoardDownloadFinish(String boardName, int listPositon) {
        Log.i("download", "finish");
        if (callback != null)
            callback.onBoardDownloadFinish(boardName, listPositon);
        showInfo(boardName + "下载完成");
    }

    @Override
    public void onBoardDownloadPause(String boardName, int listPositon) {
        Log.i("download", "pause: ");
        if (callback != null)
            callback.onBoardDownloadPause(boardName, listPositon);

        FileDownloader fileDownloader;
        fileDownloader = downloadMap.get(listPositon);
        fileDownloader.pause();

    }

    @Override
    public void onBoardDownloadResume(String boardName, int listPositon) {
        Log.i("download", "resume: ");
        if (callback != null)
            callback.onBoardDownloadResume(boardName, listPositon);

        FileDownloader fileDownloader;
        fileDownloader = downloadMap.get(listPositon);
        fileDownloader.resume();

    }

    @Override
    public void onBoardDownloadRetry(String boardName, int listPositon) {
        if (callback != null)
            callback.onBoardDownloadRetry(boardName, listPositon);
    }

    @Override
    public void onBoardDownloadStarted(final String boardName, final int listPosition) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                showInfo("开始下载" + boardName);
                // callback.onProgressBarChanged(boardName, listPosition, 3);
                callback.onBoardDownloadStarted(boardName, listPosition);
            }
        });

    }

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
}
