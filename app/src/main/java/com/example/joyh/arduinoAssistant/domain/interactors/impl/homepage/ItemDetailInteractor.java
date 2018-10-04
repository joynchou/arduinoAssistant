package com.example.joyh.arduinoAssistant.domain.interactors.impl.homepage;

import com.example.joyh.arduinoAssistant.domain.interactors.base.Interactor;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;

/**
 * Created by joyn on 2018/9/17 0017.
 * 用例7：查看对应的项目
 */

public interface ItemDetailInteractor extends Interactor {
    interface Callback{

        void onShowItem(CollectionModel collectionModel);
    }

    void interactorItemClicked(CollectionModel collectionModel);
}
