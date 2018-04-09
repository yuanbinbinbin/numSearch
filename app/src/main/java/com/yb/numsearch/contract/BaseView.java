package com.yb.numsearch.contract;

/**
 * presenter 与 Activity 交互的基础接口
 * Created by yb on 2017/7/27.
 */
public interface BaseView<T> {
    void setPresenter(T presenter);

    void showToast(String msg);
}
