package com.example.joyh.arduinoAssistant.domain.executor;

/**
 * This interface will define a class that will enable interactors to run certain operations on the main (UI) thread. For example,
 * if an interactor needs to show an object to the UI this can be used to make sure the show method is called on the UI
 * thread.
 * 这个接口定义的类将会让用例运行在ui线程上，例如
 * 如果一个用例需要在UI上显示一个对象，这个接口可以用来确保这个显示函数在UI线程中被调用
 * <p/>
 */
public interface MainThread {

    /**
     * Make runnable operation run in the main thread.
     *
     * @param runnable The runnable to run.
     */
    void post(final Runnable runnable);
}
