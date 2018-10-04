package com.example.joyh.arduinoAssistant.data.impl;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.repository.BoardRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by joyn on 2018/8/21 0021.
 */

public class BoardRepositoryImpl implements BoardRepository {


    private Context context;
   // private BoardRepository.Callback callback;
    private volatile static BoardRepositoryImpl singleInstance;

    private BoardRepositoryImpl(Context context) {
        this.context = context;
       // this.callback = callback;
    }
    public static BoardRepositoryImpl getSingleInstance(Context context){
        if(singleInstance==null){
            synchronized (BoardRepositoryImpl.class){
                if(singleInstance==null){
                    singleInstance=new BoardRepositoryImpl(context.getApplicationContext());
                }
            }
        }
        return singleInstance;
    }

    @Override
    public int getAvailableBoardAmount() {

        return this.getAvailableBoards().size();
    }

    @Override
    public List<BoardBeanModel> getAvailableBoards() {

        int amount = 0;
        List<String> boardName = this.getDownloadableBoardName(ENTRY_LEVEL);
        boardName.addAll(this.getDownloadableBoardName(ENHANCED_FEATURES));
        boardName.addAll(this.getDownloadableBoardName(RETIRED));
        List<BoardBeanModel> availableBoards = new ArrayList<>();
        BoardBeanModel singleBoard;
        String TAG = "file";

        for (int i = 0; i < boardName.size(); i++) {
            File zipFile = new File(this.boardDownloadSavePath(boardName.get(i), "zip"));
            File jpgFile = new File(this.boardImgDownloadSavePath(boardName.get(i)));
            if (zipFile.exists()) {
                if (jpgFile.exists()) {
                    singleBoard = new BoardBeanModel();
                    singleBoard.setBoardName(boardName.get(i));
                    singleBoard.setPicPath(jpgFile.toString());
                    availableBoards.add(singleBoard);

                } else {
                    Log.w(TAG, jpgFile.toString() + "is not exists");
                }
            } else {
                Log.w(TAG, zipFile.toString() + "is not exists");
            }
        }


        return availableBoards;
    }

    @Override
    public List<BoardBeanModel> getDownloadableBoards() {
        //所有板子
        List<BoardBeanModel> allBoardList = new ArrayList<>();
        //取得已经下载的板子
        List<BoardBeanModel> downloadedBoardList = this.getAvailableBoards();
        List<BoardBeanModel> downloadableBoardList = new ArrayList<>();
        List<String> boardName;
        List<String> boardURL;

        boardName = this.getDownloadableBoardName(BoardRepository.ENTRY_LEVEL);
        boardURL = this.getDownloadableBoardImgURL(BoardRepository.ENTRY_LEVEL);
        boardName.addAll(this.getDownloadableBoardName(BoardRepository.ENHANCED_FEATURES));
        boardURL.addAll(this.getDownloadableBoardImgURL(BoardRepository.ENHANCED_FEATURES));
        boardName.addAll(this.getDownloadableBoardName(BoardRepository.RETIRED));
        boardURL.addAll(this.getDownloadableBoardImgURL(BoardRepository.RETIRED));
        for (int i = 0; i < boardName.size(); i++) {
            BoardBeanModel board = new BoardBeanModel();
            board.setBoardName(boardName.get(i));
            board.setPicURL(boardURL.get(i));
            allBoardList.add(board);

        }

        allBoardList.removeAll(downloadedBoardList);
        downloadableBoardList = allBoardList;
        return downloadableBoardList;
    }

    @Override
    public void addBoardResource(BoardBeanModel board) {

    }

    @Override
    public void deleteBoardResource(String boardName) {


        String TAG = "deleteboard";
        //File deleteFile=new File(deletePath);
        File zipFile = new File(this.boardDownloadSavePath(boardName, "zip"));
        File jpgFile = new File(this.boardImgDownloadSavePath(boardName));
        if (zipFile.exists()) {
            if (jpgFile.exists()) {
                if (zipFile.delete()) {
                    Log.i(TAG, "zipfile:" + zipFile.toString() + "has been deleted");
                } else {
                    Log.w(TAG, "zipfile:" + zipFile.toString() + "doesn't been deleted");
                    //callback.onError("zipfile:" + zipFile.toString() + "doesn't been deleted");
                }
                if (jpgFile.delete()) {
                    Log.i(TAG, "jpgFile:" + jpgFile.toString() + "has been deleted");
                } else {
                    Log.w(TAG, "jpgFile:" + jpgFile.toString() + "doesn't been deleted");
                   // callback.onError("jpgFile:" + jpgFile.toString() + "doesn't been deleted");
                }

            } else {
                Log.w(TAG, "jpgfile:" + jpgFile.toString() + "doesn't exists");

            }
        } else {
            Log.w(TAG, "zipfile:" + zipFile.toString() + "doesn't exists");

        }

    }

    @Override
    public void queryBoardResource(String boradName) {

    }

    @Override
    public void changeCollectionState(CollectionModel model, boolean state) {
        ACache cache = ACache.get(context);
        LinkedHashMap<Integer, List<CollectionModel>> cacheMap;
        List<CollectionModel> cacheList;
        String oldCacheKey = model.getName() + model.getType();
        String cacheKey = "CollectionState";
        String logTag = "changeCollectionState";

        cacheMap = (LinkedHashMap<Integer, List<CollectionModel>>) cache.getAsObject(cacheKey);
        //如果有缓存
        if (cacheMap != null) {
            Log.i(logTag, "有 " + cacheKey + "的缓存");
            int positionOfModel;
            cacheList = cacheMap.get(model.getType());
            if (cacheList != null) {
                CollectionModel modelToSave = new CollectionModel();
                modelToSave.setName(model.getName());
                modelToSave.setType(model.getType());
                modelToSave.setState(state);
                modelToSave.setCollectionBean(model.getCollectionBean());
                positionOfModel = cacheList.indexOf(modelToSave);

                if (positionOfModel != -1) {

                    cacheList.set(positionOfModel, modelToSave);
                    Log.i(logTag, "hashmap内容：" + cacheMap);
                }
                else
                {
                    Log.i(logTag, "没有" + model.getName() + "的缓存存在，即将创建");
                    CollectionModel model1 = new CollectionModel();
                    model1.setName(model.getName());
                    model1.setType(model.getType());
                    model1.setState(state);
                    model1.setCollectionBean(model.getCollectionBean());
                    cacheList.add(model1);
                    Log.i(logTag, "hashmap内容：" + cacheMap);
                }
                cacheMap.put(model.getType(), cacheList);
                cache.put(cacheKey, cacheMap);
            }
            else
            {

            }

        }
        else {
            Log.i(logTag, "没有 " + cacheKey + "的缓存");
            cacheMap = new LinkedHashMap<>();
            List<CollectionModel> collectionModelList1 = new ArrayList<>();
            collectionModelList1.add(model);
            cacheMap.put(model.getType(), collectionModelList1);
            cache.put(cacheKey, cacheMap);
        }


        cache.put(cacheKey, cacheMap);
        cache.put(oldCacheKey, state);


    }
    //TODO:此方法需要改进，不能让每一个收藏对象使用一个缓存，使用合并的map缓存

    @Override
    public boolean getCollectionState(CollectionModel model) {
        ACache cache = ACache.get(context);
        String tag = model.getName() + model.getType();
        Boolean state = (Boolean) cache.getAsObject(tag);

        //TODO: 存储收藏状态信息
        //如果已经有缓存
        if (state != null) {
            Log.i("缓存", "已有" + tag + "的缓存存在");
            return state;
        }
        //没有缓存
        else {
            Log.i("缓存", "没有" + tag + "的缓存存在");
            state = false;
            cache.put(tag, state);

        }
        return state;
    }

    //TODO：这个方法可能没有用处
    @Override
    public void storeCollectionList(List<CollectionModel> stringList) {
        ACache cache = ACache.get(context);
        String tab = "CollectionState";
        HashMap<Integer, List<CollectionModel>> stringListHashMap = new LinkedHashMap<>();
        stringListHashMap.put(stringList.get(0).getType(), stringList);

        cache.put(tab, stringListHashMap);
    }

    @Override
    public List<CollectionModel> getCollectionStateList(int type) {
        ACache cache = ACache.get(context);
        String tag = "CollectionState";
        String logTag = "getCollectionStateList";
        List<CollectionModel> stringList;
        LinkedHashMap<Integer, List<CollectionModel>> stringListMap;
        stringListMap = (LinkedHashMap<Integer, List<CollectionModel>>) cache.getAsObject(tag);

        if (stringListMap != null) {
            stringList = stringListMap.get(type);

        } else {
            Log.w(logTag, "this  cache" + tag + "doesn't exist ");
            stringList = null;
        }

        return stringList;
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
               // callback.onError(e.toString());

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
                //callback.onError(e.toString());
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
        if (detailURL != null && !detailURL.isEmpty()) {
            Log.i("缓存", "有" + tag + "的缓存存在");
            detailURL = cache.getAsString(tag);
        } else {
            Log.i("缓存", "没有" + tag + "的缓存存在");
            try {
                detailURL = "";
                Document doc = Jsoup.connect("https://www.arduino.cc/en/Main/Products").timeout(10000).get();
                Element elements = doc.getElementById("entrylevel");
                Element elements2 = doc.getElementById("enhancedfeatures");
                Element elements3 = doc.getElementById("retired");
                List<Element> elementList = new ArrayList<>();
                elementList.add(elements);
                elementList.add(elements2);
                elementList.add(elements3);
                for (int l = 0; l < 3; l++) {
                    Elements titles = elementList.get(l).getElementsByAttributeValue("class", "medium-6 medium-6 small-4 columns grid-img");
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
                            break;
                        } else {

                            //System.out.println("不匹配");
                        }
                    }

                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //callback.onError(e.toString());
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
                for (int i = 0; i < titles.size(); i++) {
                    Elements resource = titles.get(i).getElementsByAttributeValue("class", "resource schematics");
                    System.out.println(resource.attr("href"));
                    allresource.add(resource.attr("href"));

                }
//
                cache.put(tag, allresource);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //callback.onError(e.toString());
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
    public String boardDownloadSavePath(String toSavedBoardName, String type) {
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
                + "." + type;

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
                + File.separator
                + toDeletedBoardName;


        return path;
    }

    @Override
    public String getBoardPDFUrl(String boardURL) {
        return null;
    }

    @Override
    public BoardBeanModel getBoardBean(String boardName) {
        ACache boardCache=ACache.get(context);
        BoardBeanModel boardBeanModel=new BoardBeanModel();
        String cacheTag="BoardBean";
        HashMap<String,BoardBeanModel> boardBeanModelMap=
                (HashMap<String,BoardBeanModel>)boardCache.getAsObject(cacheTag);
        if(boardBeanModelMap!=null){
            Log.i("缓存", "有" + cacheTag + "的缓存存在");
            boardBeanModel=boardBeanModelMap.get(boardName);


        }
        else{
            Log.i("缓存", "没有" + cacheTag + "的缓存存在");
            boardBeanModel.setBoardName("no data");
            boardBeanModel.setSchematicPath("no data");
            boardBeanModel.setPCBPath("no data");
        }
        return boardBeanModel;
    }

    @Override
    public void saveBoardBean(BoardBeanModel boardBeanModel) {
        ACache boardCache=ACache.get(context);
        String cacheTag="BoardBean";

        HashMap<String,BoardBeanModel> boardBeanModelMap=null;
        boardBeanModelMap= (HashMap<String,BoardBeanModel>)boardCache.getAsObject(cacheTag);
        //如果存在此缓存
        if(boardBeanModelMap!=null){
            Log.i("缓存", "有" + cacheTag + "的缓存存在，不需要重新创建");
            boardBeanModelMap.put(boardBeanModel.getBoardName(),boardBeanModel);
        }
        //没有则创建
        else{
            Log.i("缓存", "没有" + cacheTag + "的缓存存在，需要重新创建");
            boardBeanModelMap=new HashMap<>();
            boardBeanModelMap.put(boardBeanModel.getBoardName(),boardBeanModel);
        }


        boardCache.put(cacheTag,boardBeanModelMap);
    }
}
