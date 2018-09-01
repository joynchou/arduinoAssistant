package com.example.joyh.arduinoAssistant.domain.interactors.base;


/**
 * This is the main interface of an interactor. Each interactor serves a specific use case.
 * 这是一个用例（interactor）的主接口，每个用例为一个专门的用例服务
 * 暂且将interactor翻译为用例会比较好理解
 */
public interface Interactor {

    /**
     * This is the main method that starts an interactor. It will make sure that the interactor operation is done on a
     * background thread.
     */
    void execute();
}
