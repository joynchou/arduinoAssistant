package com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;

import java.util.List;

/**
 * Created by joyn on 2018/9/17 0017.
 * 用例1：已收藏的项目列表
 */

public interface CollectionListInteractor extends Interactor {
    interface Callback{

        /**
         * 展示已收藏的项目
         * @param collectionList 已收藏的项目
         */
        void onShowCollectionList(List<CollectionModel> collectionList);

        /**
         * 打开已收藏的项目
         * @param collectionItem
         */
        void onOpenColletionItem(CollectionModel collectionItem);
    }

    /**
     * 展示已收藏的项目
     *
     */
    void showCollectionList();

    /**
     * 打开已收藏的项目
     *
     */
    void openColletionItem();
}
