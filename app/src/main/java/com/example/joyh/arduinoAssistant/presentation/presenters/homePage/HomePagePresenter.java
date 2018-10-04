package com.example.joyh.arduinoAssistant.presentation.presenters.homePage;

import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.presentation.presenters.base.BasePresenter;
import com.example.joyh.arduinoAssistant.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by joyn on 2018/9/17 0017.
 */

public interface HomePagePresenter extends BasePresenter{
    interface View extends BaseView{

        /**
         * 展示已收藏的项目
         * @param collectionList 已收藏的项目
         */
        void onViewShowCollectionList(List<CollectionModel> collectionList);

        /**
         * 打开已收藏的项目
         * @param collectionItem
         */
        void onViewOpenColletionItem(CollectionModel collectionItem);
    }
    void presenterItemClicked(CollectionModel collectionModel);
}
