package com.example.joyh.arduinoAssistant.domain.repository;

import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;

import java.util.List;

/**
 * Created by joyn on 2018/8/21 0021.
 * 和硬件查询相关的仓库方法
 */

public interface BoardRepository {

    /**
     * 下载板子的类型，分三种类型
     */
    int ENTRY_LEVEL = 0;
    int ENHANCED_FEATURES = 1;
    int RETIRED = 2;






    /**
     * 取得已经下载的可用的板子数目
     * @return 可用的板子数目
     */
    int getAvailableBoardAmount();

    
    /**
     * 取得已经下载的可用的板子
     */
    List<BoardBeanModel> getAvailableBoards();
    
    /**
     * 取得可以供用户下载的板子，不包括已经下载的板子
     */
    List<BoardBeanModel> getDownloadableBoards();

    /**
     * 取得arduino板子下载的网址
     * @return
     */
    String getArduinoDeviceWebsite();

    /**
     * 计算需要保存的板子的保存路径
     * @param toSavedBoardName 需要保存的板子名字
     * @return
     */
    String boardDownloadSavePath(String toSavedBoardName,String type);

    /**
     * 计算需要删除的板子的删除路径
     * @param toDeletedBoardName
     * @return 删除路径
     */
    String boardDownloadDeletePath(String toDeletedBoardName);
    /**
     * 
     * @param board
     */
    void addBoardResource(BoardBeanModel board);

    /**
     * 删除板子资源
     * @param boardName 需要被删除的板子名字
     */
    void deleteBoardResource(String boardName);
    /**
     * 查找板子
     * @param boradName 板子名字
     */
    void queryBoardResource(String boradName);

    /**
     * 获取可以下载的板子名称list
     * @param boardLevel 板子等级
     * @return 板子名称list
     */
    List<String> getDownloadableBoardName(int boardLevel);

    /**
     * 获取可以下载的板子的图片的URL List
     * @param boardLevel 板子等级
     * @return 图片的URL List
     */
    List<String> getDownloadableBoardImgURL(int boardLevel);

    /**
     *获取某个板子的详细网站url
     * @param boardname 板子名称
     * @return 详细网站链接
     */
    String getBoardDetailURL(String boardname);

    /**
     *获取某个板子的所有能下载的资源
     * @param boardURL 板子的详细界面url
     * @return 可以下载资源的url list
     */
    List<String> getAllResource(String boardURL);

    /**
     *
     * @param model
     * @param state
     */
    void changeCollectionState(CollectionModel model,boolean state);
    //TODO：此函数可能不应该在此实现，因为这个不止这个模块需要用
    /**
     *获取收藏状态
     * @param model
     * @return
     */
    boolean getCollectionState(CollectionModel model);

    /**
     *获取某个类型的收藏对象的列表
     * @param type
     * @return 收藏对象列表
     */
    List<CollectionModel> getCollectionStateList(int type);

    /**
     *
     * @param stringList
     */
    void storeCollectionList(List<CollectionModel> stringList);

    /**
     * 获取板子的pdf下载链接
     * @param boardURL 板子的详细界面url，可以用@function getBoardDetailURL()来获取这个url
     * @return pdf下载链接
     */
    String getBoardPDFUrl(String boardURL);

    /**
     * 获取board对象，里面包含了所有和板子有关的信息
     * @param boardName 板子名字
     * @return 板子对象
     */
    BoardBeanModel getBoardBean(String boardName);

    /**
     * 保存board对象，会用缓存保存起来
     * @param boardBeanModel board 对象
     */
    void saveBoardBean(BoardBeanModel boardBeanModel);
}
