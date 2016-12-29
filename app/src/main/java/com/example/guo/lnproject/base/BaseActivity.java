package com.example.guo.lnproject.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.guo.lnproject.utils.utils;
import com.example.guo.lnproject.utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/9.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    protected abstract int setUpContentView();
    protected abstract void initPresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils.getAppManager().addActivity(this);
        setContentView(setUpContentView());
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        utils.getAppManager().finishActivity(this);
    }

    @Override
    public void showToast(int resId) {
        ToastUtils.showToast(this,resId);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(this,msg);
    }

    @Override
    public void showSnackBarInfo(int resId) {

    }

    @Override
    public void showSnackBarInfo(String msg) {

    }
}
