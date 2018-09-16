package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.executor.impl.ThreadExecutor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.presentation.presenters.BoardDeletePresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.impl.BoardDeletePresenterImpl;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter.DownloadedRecyclerViewAdapter;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyn on 2018/9/4 0004.
 * 已下载的板子列表fragment
 */

public class DownloadedBoardFragment extends Fragment implements BoardDeletePresenter.View,
        BoardRepository.Callback,
        DownloadedRecyclerViewAdapter.Callback {

    private TextView info;
    private BoardDeletePresenter mainPresenter;
    private MainThread mainThread;
    private BoardRepositoryImpl boardRepository;
    private RecyclerView recyclerView;
    private DownloadedRecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.resume();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view =  inflater.inflate(R.layout.fragment_downloaded_boards, container, false);

        recyclerView=view.findViewById(R.id.downloaded_recyclerView);
        info=view.findViewById(R.id.info_text);
        progressBar=view.findViewById(R.id.progressBar);
        return view;
    }

    @Override
    public void onViewDeleteBoard(String boardName) {

        recyclerViewAdapter.deleteBoard(boardName);
    }

    @Override
    public void onViewNoDownloadedBoard() {

        info.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewshowDownloadedBoards(List<BoardBeanModel> boards) {

        info.setVisibility(View.INVISIBLE);
        List<String> imgURL = new ArrayList<>();
        List<String> boardname = new ArrayList<>();
        List<String> content = new ArrayList<>();

        imgURL.clear();
        boardname.clear();
        content.clear();

        //容器初始化
        for (int i = 0; i < boards.size(); i++) {
            content.add(boards.get(i).getBoardName());
            imgURL.add(boards.get(i).getPicPath());
            boardname.add(boards.get(i).getBoardName());


        }

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerViewAdapter = new DownloadedRecyclerViewAdapter(boardname,imgURL,content,boardRepository,this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        showInfo(message);
    }

    @Override
    public void onError(String error) {
        showInfo(error);
    }

    @Override
    public void onDeleteClicked(String name) {
        mainPresenter.deleteBoard(name);
    }

    private void initPresenter() {
        Executor executor = ThreadExecutor.getInstance();
        mainThread = MainThreadImpl.getInstance();
        boardRepository = new BoardRepositoryImpl(getContext(), this);
        //presenter实例化
        mainPresenter = new BoardDeletePresenterImpl(executor, mainThread, boardRepository, this);
        mainPresenter.resume();
    }
    //以下为自定义私有方法
    private void showInfo(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
