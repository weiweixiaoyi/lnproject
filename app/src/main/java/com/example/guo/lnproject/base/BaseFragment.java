package com.example.guo.lnproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/25.
 */

public abstract class BaseFragment extends Fragment implements BaseView{
    protected View mLayoutView;
    protected abstract int setUpContentView();
    protected abstract void initPresenter();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutView = inflater.inflate(setUpContentView(),container,false);
        ButterKnife.bind(this,mLayoutView);
        initPresenter();
        return mLayoutView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @Override
    public void showToast(int resId) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showSnackBarInfo(int resId) {

    }

    @Override
    public void showSnackBarInfo(String msg) {

    }
}
