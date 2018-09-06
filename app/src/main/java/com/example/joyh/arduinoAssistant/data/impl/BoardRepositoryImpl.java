package com.example.joyh.arduinoAssistant.data.impl;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.BoardCollectionInteractor;
import com.example.joyh.arduinoAssistant.domain.model.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by joyn on 2018/8/21 0021.
 */

public class BoardRepositoryImpl implements BoardRepository {


    private Context context;
    private BoardRepository.Callback callback;
    private int availableBoardAmount;

    public BoardRepositoryImpl(Context context, BoardRepository.Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public int getAvailableBoardAmount() {

        return this.getAvailableBoards().size();
    }

    @Override
    public List<BoardBeanModelImpl> getAvailableBoards() {

        int amount=0;
        List<String> boardName=this.getDownloadableBoardName(ENTRY_LEVEL);
        List<BoardBeanModelImpl> availableBoards=new ArrayList<>();
        BoardBeanModelImpl singleBoard;
        String TAG="file";

        for(int i=0;i<boardName.size();i++) {
            File zipFile = new File(this.boardDownloadSavePath(boardName.get(i)));
            File jpgFile =new File(this.boardImgDownloadSavePath(boardName.get(i)));
            if(zipFile.exists()){
                if(jpgFile.exists()){
                    singleBoard=new BoardBeanModelImpl();
                    singleBoard.setBoardName(boardName.get(i));
                    singleBoard.setPicPath(jpgFile.toString());
                    availableBoards.add(singleBoard);

                }
                else{
                    Log.w(TAG, jpgFile.toString()+"is not exists" );
                }
            }
            else{
                Log.w(TAG, zipFile.toString()+"is not exists" );
            }
        }



        this.availableBoardAmount=amount;
        return availableBoards;
    }

    @Override
    public List<BoardBeanModelImpl> getDownloadableBoards() {
        //所有板子
        List<BoardBeanModelImpl> allBoardList =new ArrayList<>();
        //取得已经下载的板子
        List<BoardBeanModelImpl> downloadedBoardList =this.getAvailableBoards();
        List<BoardBeanModelImpl> downloadableBoardList=new ArrayList<>();
        List<String> boardName;
        List<String> boardURL;

        boardName = this.getDownloadableBoardName(BoardRepository.ENTRY_LEVEL);
        boardURL = this.getDownloadableBoardImgURL(BoardRepository.ENTRY_LEVEL);
        boardName.addAll(this.getDownloadableBoardName(BoardRepository.ENHANCED_FEATURES));
        boardURL.addAll(this.getDownloadableBoardImgURL(BoardRepository.ENHANCED_FEATURES));
//        boardName.addAll(this.getDownloadableBoardName(BoardRepository.RETIRED));
//        boardURL.addAll(this.getDownloadableBoardImgURL(BoardRepository.RETIRED));
        for (int i = 0; i < boardName.size(); i++) {
            BoardBeanModelImpl board = new BoardBeanModelImpl();
            board.setBoardName(boardName.get(i));
            board.setPicURL(boardURL.get(i));
            allBoardList.add(board);

        }

        allBoardList.removeAll(downloadedBoardList);
        downloadableBoardList=allBoardList;
        return downloadableBoardList;
    }

    @Override
    public void addBoardResource(BoardBeanModel board) {

    }

    @Override
    public void deleteBoardResource(String boardName) {

    }

    @Override
    public void queryBoardResource(String boradName) {

    }

    @Override
    public void changeCollectionState(CollectionModel model, boolean state) {
        ACache cache=ACache.get(context);
        String tag=model.getName()+model.getType();
        cache.put(tag,state);



    }

    @Override
    public boolean getCollectionState(CollectionModel model) {
        ACache cache = ACache.get(context);
        String tag=model.getName()+model.getType();
        Boolean state=(Boolean) cache.getAsObject(tag);

        //TODO: 存储收藏状态信息
        //如果已经有缓存
        if(state!=null){
            Log.i("缓存", "已有"+tag+"的缓存存在");
            return state;
        }
        //没有缓存
        else{
            Log.i("缓存", "没有"+tag+"的缓存存在");
            state=false;
            cache.put(tag,state);

        }
        return state;
    }

    @Override
    public List<String> getDownloadableBoardName(int boardLevel) {
        ArrayList<String> boardName;
        String URL = "https://www.arduino.cc/en/Main/Products";
        ACache cache = ACache.get(context);
        String tag = "DownloadableBoardName" + boardLevel;
        boardName = (ArrayList<String>) cache.getAsObject(tag);
        //已经有缓存存在
        if (boardName != null && boardName.size() != 0) {
            Log.i("缓存", "已有DownloadableBoardName的缓存存在");
            return boardName;
        }
        //没有缓存存在
        else {
            try {
                Log.i("缓存", "没有DownloadableBoardName的缓存存在");
                boardName = new ArrayList<>();
                Document doc = Jsoup.connect(URL).timeout(10000).get();
                //System.out.println(doc.toString());
                //查找所有class为"large-10.medium-12.columns"的元素
                Element elements = null;
                switch (boardLevel) {
                    case ENTRY_LEVEL:
                        elements = doc.getElementById("entrylevel");
                        break;
                    case ENHANCED_FEATURES:
                        elements = doc.getElementById("enhancedfeatures");
                        break;
                    case RETIRED:
                        elements = doc.getElementById("retired");
                        break;
                }

                //System.out.println(elements.toString());

                Elements titles = elements.
                        getElementsByAttributeValue("class", "medium-6 medium-6 small-4 columns grid-img");

                for (Element title : titles) {
                    //将找到的标签数据封装起来
                    Elements board = title.getElementsByTag("span");
                    System.out.println(board.text());
                    boardName.add(board.text());
                }

                System.out.println("over");
                cache.put(tag, boardName);
                return boardName;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                callback.onError(e.toString());

            }
        }


        return boardName;
    }

    @Override
    public List<String> getDownloadableBoardImgURL(int boardLevel) {
        String URL = "https://www.arduino.cc/en/Main/Products";
        ACache cache = ACache.get(context);
        ArrayList<String> boardImgURL;
        String tag = "DownloadableBoardImgURL" + boardLevel;
        boardImgURL = (ArrayList<String>) cache.getAsObject(tag);
        if (boardImgURL != null && boardImgURL.size() != 0) {
            Log.i("缓存", "有DownloadableBoardImgURL的缓存存在");
            return boardImgURL;
        } else {
            Log.i("缓存", "没有DownloadableBoardImgURL的缓存存在");
            boardImgURL = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(URL).timeout(10000).get();
                //System.out.println(doc.toString());
                //查找所有class为"large-10.medium-12.columns"的元素
                Element elements = null;
                switch (boardLevel) {
                    case ENTRY_LEVEL:
                        elements = doc.getElementById("entrylevel");
                        break;
                    case ENHANCED_FEATURES:
                        elements = doc.getElementById("enhancedfeatures");
                        break;
                    case RETIRED:
                        elements = doc.getElementById("retired");
                        break;
                }
                //System.out.println(elements.toString());

                Elements titles = elements.getElementsByAttributeValue("class", "medium-6 medium-6 small-4 columns grid-img");
                //在每一个找到的元素中，查找<div>标签
                //System.out.println(titles.toString());
                for (Element title : titles) {
                    //将找到的标签数据封装起来
                    String totalURL = "";
                    Elements imgURL = title.getElementsByTag("img");
                    System.out.println(imgURL.attr("src"));
                    totalURL = "https://www.arduino.cc" + imgURL.attr("src");
                    boardImgURL.add(totalURL);
                }

                System.out.println(boardImgURL);
                cache.put(tag, boardImgURL);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                callback.onError(e.toString());
            }//通过url获取到网页内容

        }

        return boardImgURL;

    }
    //bug已修复
    @Override
    public String getBoardDetailURL(String boardName) {

        ACache cache = ACache.get(context);
        String tag = boardName + "BoardDetailURL";
        String detailURL = cache.getAsString(tag);
        //如果已经存在缓存了
        if (detailURL!=null && !detailURL.isEmpty()) {
            Log.i("缓存", "有" + tag + "的缓存存在");
            detailURL=cache.getAsString(tag);
        } else {
            Log.i("缓存", "没有" + tag + "的缓存存在");
            try {
                detailURL = "";
                Document doc = Jsoup.connect("https://www.arduino.cc/en/Main/Products").timeout(3000).get();
                Element elements = doc.getElementById("entrylevel");
                Elements titles = elements.getElementsByAttributeValue("class", "medium-6 medium-6 small-4 columns grid-img");
                for (int i = 0; i < titles.size(); i++) {
                    Elements imgURL = titles.get(i).getElementsByTag("span");
                    //  System.out.println(imgURL.text());
                    if (imgURL.text().equals(boardName)) {
                        //System.out.println(titles.get(i));
                        Element boardURL = titles.get(i);
                        Elements URL = boardURL.getElementsByAttributeValue("class", "over-effect");


                        detailURL = URL.attr("href");
                        detailURL = "https://www.arduino.cc" + detailURL;
                        System.out.println(detailURL);
                        cache.put(tag, detailURL);
                    } else {

                        //System.out.println("不匹配");
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                callback.onError(e.toString());
            }
        }


        return detailURL;

    }

    @Override
    public List<String> getAllResource(String boardURL) {
        String tag = boardURL;
        Log.i("getAllResource", boardURL);
        ACache cache = ACache.get(context);
        ArrayList<String> allresource;
        allresource = (ArrayList<String>) cache.getAsObject(tag);
        if (allresource != null && allresource.size() != 0) {
            Log.i("缓存", "有" + tag + "的缓存存在");
        } else {
            Log.i("缓存", "没有" + tag + "的缓存存在");
            allresource = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(boardURL).timeout(10000).get();
                Elements titles = doc.getElementsByAttributeValue("class", "tab-container tab-name-3");
                //System.out.println(titles);
                for (int i = 0; i < titles.size(); i++) {
                    Elements resource = titles.get(i).getElementsByAttributeValue("class", "resource eagle");
                    System.out.println(resource.attr("href"));
                    allresource.add(resource.attr("href"));

                }
//
                cache.put(tag, allresource);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                callback.onError(e.toString());
            }
        }


        Log.i("allresource", allresource.toString());


        return allresource;

    }

    @Override
    public String getArduinoDeviceWebsite() {
        String URL = "https://www.arduino.cc/en/Main/Products";
        return URL;
    }

    @Override
    public String boardDownloadSavePath(String toSavedBoardName) {
        String path;
        String defaultRootPath =
                Environment.getExternalStorageDirectory().getPath();
        path = defaultRootPath
                + File.separator
                + "ArduinoResource"
                + File.separator
                + "boardResource"
                + File.separator
                + toSavedBoardName
                + File.separator
                + toSavedBoardName
                + ".zip";
        return path;
    }
    public String boardImgDownloadSavePath(String toSavedBoardName) {
        String path;
        String defaultRootPath =
                Environment.getExternalStorageDirectory().getPath();
        path = defaultRootPath
                + File.separator
                + "ArduinoResource"
                + File.separator
                + "boardResource"
                + File.separator
                + toSavedBoardName
                + File.separator
                + toSavedBoardName
                + ".jpg";
        return path;
    }

    @Override
    public String boardDownloadDeletePath(String toDeletedBoardName) {
        String path;
        String defaultRootPath =
                Environment.getExternalStorageDirectory().getPath();
        path = defaultRootPath
                + File.separator
                + "ArduinoResource"
                + File.separator
                + "boardResource"

        ;
        return path;
    }
}
