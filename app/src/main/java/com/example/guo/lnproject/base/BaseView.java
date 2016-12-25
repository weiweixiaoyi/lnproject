package com.example.guo.lnproject.base;

/**
 * Created by Administrator on 2016/12/25.
 */

public interface BaseView {
    void showToast(int resId);
    void showToast(String msg);
    void showSnackBarInfo(int resId);
    void showSnackBarInfo(String msg);
}
