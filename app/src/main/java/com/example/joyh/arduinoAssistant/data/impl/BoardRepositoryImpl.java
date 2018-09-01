package com.example.joyh.arduinoAssistant.data.impl;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.joyh.arduinoAssistant.domain.model.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;
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

    public BoardRepositoryImpl(Context context, BoardRepository.Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public int getAvailableBoardAmount() {

        return 0;
    }

    @Override
    public List<BoardBeanModelImpl> getAvailableBoards() {
        ACache cache = ACache.get(context);

        return null;
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

    @Override
    public String getBoardDetailURL(String boardName) {

        ACache cache = ACache.get(context);
        String tag = boardName + "BoardDetailURL";
        String detailURL = null;
        //如果已经存在缓存了
        if (cache.getAsString(tag).equals("") == false) {
            Log.i("缓存", "有" + tag + "的缓存存在");
        } else {
            Log.i("缓存", "没有" + tag + "的缓存存在");
            try {
                detailURL = "";
                Document doc = Jsoup.connect(this.getArduinoDeviceWebsite()).timeout(10000).get();
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
                        // detailURL="error";
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
