
package com.example.joyh.arduinoAssistant.presentation.ui.activities.homepage.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.executor.impl.ThreadExecutor;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;
import com.example.joyh.arduinoAssistant.presentation.presenters.homePage.HomePagePresenter;
import com.example.joyh.arduinoAssistant.presentation.presenters.homePage.impl.HomePagePresenterImpl;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.about.aboutSoftWareActivity;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.apiinfo.APIInfoActivity;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.activity.BoardDetailActivity;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.activity.HardWareInfoActivity;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter.BoardDetailRecyclerViewAdapter;
import com.example.joyh.arduinoAssistant.presentation.ui.activities.homepage.adapter.CollectionListAdapter;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

import static com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel.COLLECTION_TYPE_BOARD;

/**
 * 主页界面
 */

public class homePageActivity extends AppCompatActivity implements HomePagePresenter.View, CollectionListAdapter.Callback {
    //侧边菜单栏
    private DrawerLayout mDrawerLayout;
    private RecyclerView collectionList;
    private RecyclerView historyList;
    private RecyclerView recommendList;
    private BoardRepository boardRepository;
    private HomePagePresenterImpl homePagePresenter;
    private CollectionListAdapter collectionListAdapter;
    private MainThread mainThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        StatService.start(this);
        //        // 获取测试设备ID
        //        String testDeviceId = StatService.getTestDeviceId(this);
        //           // 日志输出
        //        android.util.Log.d("BaiduMobStat", "Test DeviceId : " + testDeviceId);

        initToorbor();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        collectionList = findViewById(R.id.homepageCollectionList);

        initPresenter();
        initCollectionList(collectionList);
        initHistoryList(historyList);
        initRecommendList(recommendList);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        homePagePresenter.resume();

    }

    @Override
    protected void onResume() {
        super.onResume();
        homePagePresenter.resume();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    //顶部工具条被选中的复写
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_setting://设置按钮
                //TODO:设置功能待完善
                // android.app.FragmentManager fragmentManager = getFragmentManager();
                //settingFragment settingFragment = new settingFragment();
                //android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // fragmentTransaction
                //         .replace(R.id.frameLayout ,settingFragment);
                //fragmentTransaction.addToBackStack(null);
                //  fragmentTransaction.commit();


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 设置左边抽屉菜单上下文 */
    private void setupDrawerContent(NavigationView navigationView) {
        final View nav = navigationView;
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.about_software:
                                intent =
                                        new Intent
                                                (homePageActivity.this, aboutSoftWareActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_API_info:
                                intent = new Intent(homePageActivity.this, APIInfoActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_BBS:
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://www.arduino.cn/"));
                                startActivity(intent);
                                break;
                            case R.id.nav_hardware_info:
                                intent = new Intent(homePageActivity.this, HardWareInfoActivity.class);
                                startActivity(intent);
                        }

                        return true;
                    }
                });
    }

    /////presenter中的接口实现////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }


    @Override
    public void onViewShowCollectionList(List<CollectionModel> collectionList) {

        List<CollectionModel> collectionModelList=new ArrayList<>();

            if(collectionList!=null) {
                CollectionModel collectionModel;
                for (int i = 0; i < collectionList.size(); i++) {
                    collectionModel = collectionList.get(i);
                    if (collectionModel.getState()) {
                        collectionModelList.add(collectionList.get(i));
                    }
                }
                collectionListAdapter = new CollectionListAdapter(collectionModelList, this);
                this.collectionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                this.collectionList.setAdapter(collectionListAdapter);

            }
            //没有已收藏的项目时
            else{

            }
    }

    @Override
    public void onViewOpenColletionItem(CollectionModel collectionItem) {

        switch(collectionItem.getType()){
            case COLLECTION_TYPE_BOARD:
                BoardBeanModel boardBeanModel=(BoardBeanModel)collectionItem.getCollectionBean();
                Intent intent=new Intent(homePageActivity.this, BoardDetailActivity.class);
                intent.putExtra("com.example.joyn.arduinoAssistant:boardname",boardBeanModel.getBoardName());
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onItemClicked(CollectionModel collectionModel) {
        homePagePresenter.presenterItemClicked(collectionModel);
    }

    /////私有方法///////////////////////////////////////////////////////////////////////////////////////////////
    private void initToorbor() {
        // StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.primary), false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("主页");
        //设置顶部工具条
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化presenter
     */
    private void initPresenter() {
        Executor executor = ThreadExecutor.getInstance();
        mainThread = MainThreadImpl.getInstance();
        boardRepository = BoardRepositoryImpl.getSingleInstance(getApplicationContext());
        homePagePresenter = new HomePagePresenterImpl(executor, mainThread, boardRepository, this);

    }

    /**
     * 初始化收藏列表
     *
     * @param recyclerView 收藏列表的recyclerview
     */
    private void initCollectionList(RecyclerView recyclerView) {

        recyclerView.setNestedScrollingEnabled(false);

    }

    /**
     * 初始化历史列表
     *
     * @param recyclerView
     */
    private void initHistoryList(RecyclerView recyclerView) {


    }

    /**
     * 初始化推荐列表
     *
     * @param recyclerView
     */
    private void initRecommendList(RecyclerView recyclerView) {

    }
}
