package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.executor.impl.ThreadExecutor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.SharableBeanModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.presentation.presenters.hardwareInfo.BoardResourcePresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.hardwareInfo.impl.BoardResourcePresenterImpl;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter.BoardDetailRecyclerViewAdapter;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;

import static com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel.COLLECTION_TYPE_BOARD;


/**
 * Created by joyn on 2018/9/9 0009.
 * 启动此activity需要提供boardname，否则会错误
 */

public class BoardDetailActivity extends AppCompatActivity implements BoardResourcePresenter.View,
        BoardDetailRecyclerViewAdapter.Callback {

    private String thisBoardName;
    private BoardResourcePresenter mainPresenter;
    private BoardRepository boardRepository;
    private RecyclerView recyclerView;
    private BoardDetailRecyclerViewAdapter recyclerViewAdapter;
    private Button testButton;
    private CollectionModel collectionModel;
    MainThread mainThread = MainThreadImpl.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        Intent intent = getIntent();
        thisBoardName = intent.getStringExtra("com.example.joyn.arduinoAssistant:boardname");
        if (thisBoardName != null) {
            String TAG = "boardName";
            Log.i(TAG, thisBoardName);
        }
        else{
            showError("指定的boardname为空！");
            Log.e("空", "指定的boardname为空！" );
        }

        initPresenter();
        initToolbar(thisBoardName);

        collectionModel=new CollectionModel();
        collectionModel.setName(thisBoardName);
        collectionModel.setType(COLLECTION_TYPE_BOARD);
        recyclerView = findViewById(R.id.recyclerview);
        // recyclerView.setAdapter(new BoardDetailRecyclerViewAdapter(this,));
        testButton = findViewById(R.id.button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardDetailActivity.this, ResourceWindow.class);
                intent.putExtra("pdf", boardRepository.boardDownloadSavePath(thisBoardName, "pdf"));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.resume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (boardRepository.getCollectionState(collectionModel)) {
            getMenuInflater().inflate(R.menu.menu_board_detail_list_star, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.menu_board_detail_list_unstar, menu);

        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(boardRepository.getCollectionState(collectionModel)){

            menu.findItem( R.id.ic_star).setIcon(R.drawable.ic_favorite_red);

        }
        else{
            menu.findItem( R.id.ic_star).setIcon(R.drawable.ic_favorite_border);

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_star:
                if(boardRepository.getCollectionState(collectionModel)){
                   item.setIcon(R.drawable.ic_favorite_border);

                }
                else{
                    item.setIcon(R.drawable.ic_favorite_red);

                }
                boardRepository.changeCollectionState(collectionModel,!boardRepository.getCollectionState(collectionModel));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onViewShowResource(SharableBeanModel sharable) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    @Override
    public void hideProgress() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onViewShowDetailList(BoardBeanModel beanModel) {

        recyclerViewAdapter=new BoardDetailRecyclerViewAdapter(this,beanModel,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onShareBtnClicked(BoardBeanModel boardBeanModel) {
        mainPresenter.PresenterShareClicked(boardBeanModel);
    }

    @Override
    public void onItemClicked(BoardBeanModel boardBeanModel) {
        mainPresenter.PresenterItemClicked(boardBeanModel);
    }

    @Override
    public void onViewShowBoardCollectionState(boolean state) {

        invalidateOptionsMenu();
    }

    @Override
    public void onViewShareResource() {

    }



    @Override
    public void showError(String message) {

        mainThread.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void initPresenter() {
        Executor executor = ThreadExecutor.getInstance();

        boardRepository = BoardRepositoryImpl.getSingleInstance(this);
        mainPresenter = new BoardResourcePresenterImpl(thisBoardName, executor, mainThread, boardRepository, this);
        mainPresenter.resume();
        mainPresenter.PresenterShowBoardDetailList(thisBoardName);
    }

    private void initToolbar(String toolbarName) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
