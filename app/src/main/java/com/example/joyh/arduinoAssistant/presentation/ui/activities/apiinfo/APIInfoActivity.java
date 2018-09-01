package com.example.joyh.arduinoAssistant.presentation.ui.activities.apiinfo;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.aitangba.swipeback.SwipeBackActivity;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.executor.impl.ThreadExecutor;
import com.example.joyh.arduinoAssistant.presentation.presenters.APIInfoPresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.impl.APIInfoPresenterImpl;
import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;
import com.githang.statusbar.StatusBarCompat;

import java.util.List;

/**
 * Created by joyn on 2018/7/27 0027.
 * 此activity是用来显示api模块的
 */

public class APIInfoActivity extends SwipeBackActivity implements APIInfoPresenter.View {
    private APIInfoPresenter mainPresenter;
    private ProgressBar progressBar;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("A", "APIINFO AT被创建");
        setContentView(R.layout.activity_api_info);
        Executor executor = ThreadExecutor.getInstance();
        MainThread mainThread = MainThreadImpl.getInstance();
        //presenter实例化
        mainPresenter = new APIInfoPresenterImpl(executor, mainThread, this);
        final Toolbar toolbar = findViewById(R.id.toolbar);

        progressBar = findViewById(R.id.progressBar);
        Spinner spinner = findViewById(R.id.spinner);

        FloatingActionButton floatingActionButton = findViewById(R.id.button_search);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.performClick();
            }
        });

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), false);
        toolbar.setTitle("API查询");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.resume();
    }

    @Override
    public void showNoResult() {
        Toast.makeText(APIInfoActivity.this, "没有任何匹配结果", Toast.LENGTH_SHORT).show();

        searchView.setQueryHint("没有任何匹配结果");
    }

    @Override
    public void hideSearchEditWindow() {

    }

    @Override
    public void showResults(List<String> result) {

    }

    @Override
    public void showSearchEditWindow() {

    }

    @Override
    public void showProgress() {
        Log.i("progress", "进度条被打开");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        Log.i("progress", "进度条被关闭");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.menu_apiinfo_bar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); //  iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                Toast.makeText(APIInfoActivity.this, query, Toast.LENGTH_SHORT).show();
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入框内容改变的时候回调
                Log.i("searchview", "内容: " + newText);
                search(newText);
                return true;
            }
        });
        return true;
    }

    private void search(String text) {

        mainPresenter.search(text);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Log.i("info", "搜索键被按下");


                break;
            case R.id.homeAsUp:
                Log.i("info", "返回键被按下");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //
    private void setupViewPager(ViewPager viewPager) {
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ArduinoFragment(), "Arduino");
        //adapter.addFragment(new ArduinoFragment(),"C/C++");
        viewPager.setAdapter(adapter);
    }
}

