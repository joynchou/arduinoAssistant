package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.example.joyh.arduinoAssistant.presentation.presenters.HardwareInfoPresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.impl.HardwareInfoPresenterImpl;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.apiinfo.ArduinoFragment;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.apiinfo.TabFragmentPagerAdapter;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by joyn on 2018/8/11 0011.
 */

public class HardWareInfoActivity extends SwipeBackActivity implements HardwareInfoPresenter.View,
        BoardRepository.Callback,
        AvailableBoardsRecyclerViewAdapterInterface.Callback {
    private HardwareInfoPresenter mainPresenter;
    private BoardRepositoryImpl boardRepository;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;
    private ArduinoFragment arduinoFragment;
    private Intent intent;
    private TextView info;
    private RecyclerView recyclerView;
    private AvailableBoardsRecyclerViewAdapter recyclerViewAdapter;

    @OnClick(R.id.button_search)
    void buttonSearch() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardwareinfo);
        ButterKnife.bind(this);
        initPresenter();
        initToorbor();
        intent = new Intent(HardWareInfoActivity.this, BoardManagerActivity.class);
        recyclerView = findViewById(R.id.RecyclerView);
        progressBar = findViewById(R.id.progressBar);
        info = findViewById(R.id.info_text);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), false);


        ViewPager viewPager = findViewById(R.id.viewPager);
//        if (viewPager != null) {
//            setupViewPager(viewPager);
//        } else {
//            Log.w("viwepager", "onCreate: " + "viewpager is null");
//            showInfo("onCreate: " + "viewpager is null");
//        }

        mainPresenter.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.menu_hardware_info_bar, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); //  iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                Toast.makeText(HardWareInfoActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入框内容改变的时候回调
                Log.i("searchview", "内容: " + newText);
                return true;
            }
        });
        return true;
    }

    //顶部toorbar选项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Log.i("info", "搜索键被按下");
                break;
            case R.id.homeAsUp:
                break;
            case R.id.menu_downloader:
                Intent intent = new Intent(HardWareInfoActivity.this, BoardManagerActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //以下为presenter中的回调函数实现
    @Override
    public void onShowNoAvailableBoard() {
        showInfo(getString(R.string.toast_no_availabel_board));
        intent.putExtra("available_board", 0);
        info.setVisibility(View.VISIBLE);

    }

    @Override
    public void onOpenBoardDownloadManager() {

        startActivity(intent);
    }

    @Override
    public void onShowBoards(List<BoardBeanModelImpl> boards,List<Boolean> collectionState ) {
        List<String> boardName = new ArrayList<>();
        List<String> boardImg = new ArrayList<>();
        for (int i = 0; i < boards.size(); i++) {
            boardName.add(boards.get(i).getBoardName());
            boardImg.add(boards.get(i).getPicPath());
        }
        info.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setVisibility(View.VISIBLE);
        recyclerViewAdapter = new AvailableBoardsRecyclerViewAdapter(boardName, boardImg, collectionState,this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onStarButtonClicked(String boardName) {
        mainPresenter.presenterStarButtonClicked(boardName);
    }

    @Override
    public void onViewChangeCollectionState(String boardName, boolean state) {
        recyclerViewAdapter.changBoardCollectionState(boardName, state);
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
        Log.w("网络错误...", "showError: " + message);
        showInfo(message);
    }

    @Override
    public void onError(String error) {
        showError(error);
    }

    //以下为自定义私有方法
    private void showInfo(String message) {
        Toast.makeText(HardWareInfoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void initToorbor() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("硬件查询");
        //   toolbar.setTitleTextColor(R.color.window_background);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initPresenter() {
        Executor executor = ThreadExecutor.getInstance();
        MainThread mainThread = MainThreadImpl.getInstance();
        boardRepository = new BoardRepositoryImpl(getApplicationContext(), this);
        //presenter实例化
        mainPresenter = new HardwareInfoPresenterImpl(executor, mainThread, boardRepository, this);
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putInt("test", 123);

        ArduinoBoardFragment arduinoFragment = new ArduinoBoardFragment();
        arduinoFragment.setArguments(bundle);

        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        //   adapter.addFragment(arduinoFragment, "Arduino开发板");
        //adapter.addFragment(new ArduinoFragment(), "常用元件");
        //   viewPager.setAdapter(adapter);
    }
}
