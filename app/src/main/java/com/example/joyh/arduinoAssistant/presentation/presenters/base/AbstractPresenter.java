package com.example.joyh.arduinoAssistant.presentation.presenters.base;


import com.example.joyh.arduinoAssistant.domain.executor.Executor;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;

/**
 * This is a base class for all presenters which are communicating with interactors. This base class will hold a
 * reference to the Executor and MainThread objects that are needed for running interactors in a background thread.
 * 这是一个为所有 用来和用例交互的presenters 准备的基类
 * 这个基类会保存一个  到Executor和MainThread对象的引用，这些应用的作用是为了
 * 让用例能在后台线程中运行
 */
public abstract class AbstractPresenter {
    protected Executor   mExecutor;
    protected MainThread mMainThread;

    protected AbstractPresenter(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }
}
